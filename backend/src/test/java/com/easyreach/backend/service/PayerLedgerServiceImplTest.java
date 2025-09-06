package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.ledger.ApplyPaymentRequest;
import com.easyreach.backend.dto.ledger.PayerLedgerSummaryDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.entity.PayerSettlement;
import com.easyreach.backend.entity.VehicleEntry;
import com.easyreach.backend.mapper.VehicleEntryMapper;
import com.easyreach.backend.repository.PayerLedgerSummary;
import com.easyreach.backend.repository.PayerSettlementRepository;
import com.easyreach.backend.repository.VehicleEntryRepository;
import com.easyreach.backend.service.impl.PayerLedgerServiceImpl;
import com.easyreach.backend.util.CodeGenerator;
import com.easyreach.backend.security.CompanyContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayerLedgerServiceImplTest {

    @Mock
    private VehicleEntryRepository vehicleEntryRepository;
    @Mock
    private VehicleEntryMapper vehicleEntryMapper;
    @Mock
    private PayerSettlementRepository payerSettlementRepository;
    @Mock
    private CodeGenerator codeGenerator;

    @InjectMocks
    private PayerLedgerServiceImpl service;

    @BeforeEach
    void setup() {
        CompanyContext.setCompanyId("cmp");
    }

    @AfterEach
    void tearDown() {
        CompanyContext.clear();
    }

    @Test
    void getLedgerForPayerUsesCurrentCompany() {
        VehicleEntry entry = new VehicleEntry();
        Page<VehicleEntry> page = new PageImpl<>(List.of(entry));
        when(vehicleEntryRepository.findByCompanyUuidAndPayerIdAndDeletedIsFalseOrderByCreatedAtDesc(eq("cmp"), eq("payer1"), any(PageRequest.class)))
                .thenReturn(page);
        when(vehicleEntryMapper.toDto(entry)).thenReturn(new VehicleEntryResponseDto());

        ApiResponse<Page<VehicleEntryResponseDto>> resp = service.getLedgerForPayer("payer1", PageRequest.of(0, 10));

        assertTrue(resp.isSuccess());
        verify(vehicleEntryRepository).findByCompanyUuidAndPayerIdAndDeletedIsFalseOrderByCreatedAtDesc(eq("cmp"), eq("payer1"), any(PageRequest.class));
        verify(vehicleEntryMapper).toDto(entry);
    }

    @Test
    void getSummaryMapsRows() {
        PayerLedgerSummary row = mock(PayerLedgerSummary.class);
        when(row.getPayerId()).thenReturn("p1");
        when(row.getPayerName()).thenReturn("Payer One");
        when(row.getPendingAmt()).thenReturn(BigDecimal.TEN);
        when(vehicleEntryRepository.summarizePendingByPayer("cmp")).thenReturn(List.of(row));

        ApiResponse<List<PayerLedgerSummaryDto>> resp = service.getSummary();

        assertTrue(resp.isSuccess());
        assertThat(resp.getData()).hasSize(1);
        assertEquals("Payer One", resp.getData().get(0).getPayerName());
    }

    @Test
    void applyPaymentThrowsForNonPositive() {
        ApplyPaymentRequest request = new ApplyPaymentRequest();
        request.setAmount(BigDecimal.ZERO);
        assertThrows(IllegalArgumentException.class, () -> service.applyPayment("p1", request));
    }

    @Test
    void applyPaymentDistributesAndSaves() {
        ApplyPaymentRequest request = new ApplyPaymentRequest();
        request.setAmount(new BigDecimal("70"));
        request.setSettlementType("CASH");

        when(codeGenerator.generate("PS")).thenReturn("PS1");

        VehicleEntry e1 = new VehicleEntry();
        e1.setPaidAmount(BigDecimal.ZERO);
        e1.setPendingAmt(new BigDecimal("50"));
        e1.setIsSettled(false);

        VehicleEntry e2 = new VehicleEntry();
        e2.setPaidAmount(BigDecimal.ZERO);
        e2.setPendingAmt(new BigDecimal("50"));
        e2.setIsSettled(false);

        when(vehicleEntryRepository.findByCompanyUuidAndPayerIdAndDeletedIsFalseAndIsSettledFalseOrderByEntryTimeAsc("cmp", "p1"))
                .thenReturn(List.of(e1, e2));

        service.applyPayment("p1", request);

        assertEquals(new BigDecimal("50"), e1.getPaidAmount());
        assertEquals(BigDecimal.ZERO, e1.getPendingAmt());
        assertTrue(e1.getIsSettled());
        assertEquals("CASH", e1.getSettlementType());
        assertNotNull(e1.getSettlementDate());

        assertEquals(new BigDecimal("20"), e2.getPaidAmount());
        assertEquals(new BigDecimal("30"), e2.getPendingAmt());
        assertFalse(e2.getIsSettled());
        assertNull(e2.getSettlementType());

        verify(payerSettlementRepository).save(any(PayerSettlement.class));
        verify(vehicleEntryRepository).saveAll(List.of(e1, e2));
    }
}

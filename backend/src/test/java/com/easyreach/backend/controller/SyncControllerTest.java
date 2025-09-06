package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.SyncRequestDto;
import com.easyreach.backend.dto.SyncResponseDto;
import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SyncControllerTest {

    @Mock private CompanyService companyService;
    @Mock private DailyExpenseService dailyExpenseService;
    @Mock private DieselUsageService dieselUsageService;
    @Mock private EquipmentUsageService equipmentUsageService;
    @Mock private ExpenseMasterService expenseMasterService;
    @Mock private InternalVehicleService internalVehicleService;
    @Mock private PayerService payerService;
    @Mock private PayerSettlementService payerSettlementService;
    @Mock private VehicleEntryService vehicleEntryService;
    @Mock private VehicleTypeService vehicleTypeService;
    @Mock private SyncDownloadService syncDownloadService;

    private SyncController controller;

    @BeforeEach
    void setUp() {
        controller = new SyncController(
                companyService,
                dailyExpenseService,
                dieselUsageService,
                equipmentUsageService,
                expenseMasterService,
                internalVehicleService,
                payerService,
                payerSettlementService,
                vehicleEntryService,
                vehicleTypeService,
                syncDownloadService);
    }

    @Test
    void sync_returns_null_counts_when_lists_absent() {
        SyncRequestDto request = new SyncRequestDto();

        ResponseEntity<ApiResponse<SyncResponseDto>> entity = controller.sync(request);
        SyncResponseDto response = entity.getBody().getData();

        assertNull(response.getCompanies());
        assertNull(response.getDailyExpenses());
        assertNull(response.getDieselUsages());
        assertNull(response.getEquipmentUsages());
        assertNull(response.getExpenseMasters());
        assertNull(response.getInternalVehicles());
        assertNull(response.getPayers());
        assertNull(response.getPayerSettlements());
        assertNull(response.getVehicleEntries());
        assertNull(response.getVehicleTypes());

        verifyNoInteractions(
                companyService,
                dailyExpenseService,
                dieselUsageService,
                equipmentUsageService,
                expenseMasterService,
                internalVehicleService,
                payerService,
                payerSettlementService,
                vehicleEntryService,
                vehicleTypeService);
    }

    @Test
    void sync_sets_counts_for_processed_lists() {
        SyncRequestDto request = new SyncRequestDto();
        request.setCompanies(List.of(new CompanyRequestDto()));
        request.setPayers(List.of(new PayerRequestDto()));
        request.setVehicleEntries(Collections.emptyList());

        when(companyService.bulkSync(any())).thenReturn(2);
        when(payerService.bulkSync(any())).thenReturn(1);
        when(vehicleEntryService.bulkSync(any())).thenReturn(0);

        ResponseEntity<ApiResponse<SyncResponseDto>> entity = controller.sync(request);
        SyncResponseDto response = entity.getBody().getData();

        assertEquals(2, response.getCompanies());
        assertEquals(1, response.getPayers());
        assertEquals(0, response.getVehicleEntries());

        assertNull(response.getDailyExpenses());
        assertNull(response.getDieselUsages());
        assertNull(response.getEquipmentUsages());
        assertNull(response.getExpenseMasters());
        assertNull(response.getInternalVehicles());
        assertNull(response.getPayerSettlements());
        assertNull(response.getVehicleTypes());
    }
}


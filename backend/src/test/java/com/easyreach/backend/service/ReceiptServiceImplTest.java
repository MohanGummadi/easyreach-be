package com.easyreach.backend.service;

import com.easyreach.backend.dto.receipt.ReceiptDto;
import com.easyreach.backend.entity.Order;
import com.easyreach.backend.entity.Receipt;
import com.easyreach.backend.repository.OrderRepository;
import com.easyreach.backend.repository.ReceiptRepository;
import com.easyreach.backend.service.impl.ReceiptServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceImplTest {

    @Mock
    private ReceiptRepository repository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ReceiptServiceImpl service;

    @BeforeEach
    void setUp() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken("test-user", "pw"));
        SecurityContextHolder.setContext(context);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createSavesReceiptWithDefaults() {
        ReceiptDto dto = ReceiptDto.builder()
                .orderId("ord1")
                .sandQuantity("10")
                .dispatchDateTime(LocalDateTime.now())
                .driverName("Drv")
                .driverMobile("222")
                .vehicleNo("VH1")
                .qrUrl("qr")
                .build();

        Order order = Order.builder()
                .orderId("ORD1")
                .customerName("Cust")
                .customerMobile("111")
                .supplyPoint("SP")
                .fullAddress("Addr")
                .tripNo(0)
                .build();

        when(orderRepository.findByOrderIdIgnoreCase("ord1")).thenReturn(java.util.Optional.of(order));
        when(repository.save(any(Receipt.class))).thenAnswer(inv -> inv.getArgument(0));

        Receipt saved = service.create(dto);

        assertEquals("ORD1", saved.getOrderId());
        assertEquals("10", saved.getSandQuantity());
        assertEquals("SP", saved.getSupplyPoint());
        assertEquals("18.4060366,83.9543993 Thank you", saved.getFooterLine());
        assertEquals("test-user", saved.getCreatedBy());
        verify(orderRepository).save(any(Order.class));
        verify(repository).save(any(Receipt.class));
    }

    @Test
    void findByOrderIdMapsEntityToDto() {
        Receipt entity = Receipt.builder()
                .id(1L)
                .orderId("ORD1")
                .tripNo("T1")
                .customerName("Name")
                .customerMobile("111")
                .sandQuantity("10")
                .supplyPoint("SP")
                .dispatchDateTime(LocalDateTime.now())
                .driverName("D")
                .driverMobile("M")
                .vehicleNo("V")
                .address("Addr")
                .footerLine("F")
                .qrUrl("qr")
                .createdBy("user")
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
        when(repository.findFirstByOrderIdIgnoreCase("ORD1")).thenReturn(Optional.of(entity));

        ReceiptDto result = service.findByOrderId("ORD1");

        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getOrderId(), result.getOrderId());
        assertNull(result.getDriverName());
        assertNull(result.getDriverMobile());
        assertNull(result.getVehicleNo());
    }

    @Test
    void findByOrderIdReturnsNullWhenMissing() {
        when(repository.findFirstByOrderIdIgnoreCase("X")).thenReturn(Optional.empty());
        assertNull(service.findByOrderId("X"));
    }
}

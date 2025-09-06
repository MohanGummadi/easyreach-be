package com.easyreach.backend.entity;

import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class VehicleEntryEntityTest {

    @Test
    void entityAnnotationAndBuilder() {
        assertThat(VehicleEntry.class.isAnnotationPresent(Entity.class)).isTrue();
        OffsetDateTime now = OffsetDateTime.now();
        VehicleEntry entry = VehicleEntry.builder()
                .entryId("e1")
                .companyUuid("c1")
                .payerId("p1")
                .vehicleNumber("ABC123")
                .vehicleType("Truck")
                .fromAddress("from")
                .toAddress("to")
                .driverName("Driver")
                .driverContactNo("999")
                .commission(BigDecimal.ONE)
                .beta(BigDecimal.ONE)
                .amount(BigDecimal.TEN)
                .paytype("CASH")
                .entryDate(LocalDate.now())
                .entryTime(now)
                .paidAmount(BigDecimal.ONE)
                .pendingAmt(BigDecimal.ZERO)
                .isSettled(false)
                .isSynced(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        assertThat(entry.getVehicleNumber()).isEqualTo("ABC123");
    }
}

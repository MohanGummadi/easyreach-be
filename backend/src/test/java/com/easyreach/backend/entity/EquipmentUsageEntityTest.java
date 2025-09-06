package com.easyreach.backend.entity;

import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EquipmentUsageEntityTest {

    @Test
    void entityAnnotationAndBuilder() {
        assertThat(EquipmentUsage.class.isAnnotationPresent(Entity.class)).isTrue();
        OffsetDateTime now = OffsetDateTime.now();
        EquipmentUsage usage = EquipmentUsage.builder()
                .equipmentUsageId("u1")
                .equipmentName("Excavator")
                .equipmentType("Heavy")
                .startKm(BigDecimal.ONE)
                .endKm(BigDecimal.TEN)
                .startTime(now)
                .endTime(now.plusHours(1))
                .date(now)
                .companyUuid("c1")
                .isSynced(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        assertThat(usage.getEquipmentName()).isEqualTo("Excavator");
    }
}

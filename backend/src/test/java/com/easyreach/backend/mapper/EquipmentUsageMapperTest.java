package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.equipment_usage.EquipmentUsageRequestDto;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageResponseDto;
import com.easyreach.backend.entity.EquipmentUsage;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EquipmentUsageMapperTest {

    private final EquipmentUsageMapper mapper = Mappers.getMapper(EquipmentUsageMapper.class);

    @Test
    void toEntity_and_toDto() {
        String equipmentUsageId = "eu1";
        String equipmentName = "Crane";
        String equipmentType = "Heavy";
        BigDecimal startKm = new BigDecimal("10");
        BigDecimal endKm = new BigDecimal("20");
        OffsetDateTime startTime = OffsetDateTime.parse("2023-05-01T09:00:00Z");
        OffsetDateTime endTime = OffsetDateTime.parse("2023-05-01T17:00:00Z");
        OffsetDateTime date = OffsetDateTime.parse("2023-05-01T00:00:00Z");
        String companyUuid = "comp1";
        Boolean isSynced = true;
        String createdBy = "creator";
        OffsetDateTime createdAt = OffsetDateTime.parse("2023-05-02T00:00:00Z");
        String updatedBy = "upd";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-05-03T00:00:00Z");
        Boolean deleted = true;
        OffsetDateTime deletedAt = OffsetDateTime.parse("2023-05-04T00:00:00Z");
        Long changeId = 6L;

        EquipmentUsageRequestDto dto = new EquipmentUsageRequestDto();
        dto.setEquipmentUsageId(equipmentUsageId);
        dto.setEquipmentName(equipmentName);
        dto.setEquipmentType(equipmentType);
        dto.setStartKm(startKm);
        dto.setEndKm(endKm);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setDate(date);
        dto.setCompanyUuid(companyUuid);
        dto.setIsSynced(isSynced);
        dto.setCreatedBy(createdBy);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedBy(updatedBy);
        dto.setUpdatedAt(updatedAt);
        dto.setDeleted(deleted);
        dto.setDeletedAt(deletedAt);
        dto.setChangeId(changeId);

        EquipmentUsage expectedEntity = EquipmentUsage.builder()
                .equipmentUsageId(equipmentUsageId)
                .equipmentName(equipmentName)
                .equipmentType(equipmentType)
                .startKm(startKm)
                .endKm(endKm)
                .startTime(startTime)
                .endTime(endTime)
                .date(date)
                .companyUuid(companyUuid)
                .isSynced(isSynced)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .deleted(deleted)
                .deletedAt(deletedAt)
                .changeId(changeId)
                .build();

        EquipmentUsage entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        EquipmentUsageResponseDto expectedDto = new EquipmentUsageResponseDto();
        expectedDto.setEquipmentUsageId(equipmentUsageId);
        expectedDto.setEquipmentName(equipmentName);
        expectedDto.setEquipmentType(equipmentType);
        expectedDto.setStartKm(startKm);
        expectedDto.setEndKm(endKm);
        expectedDto.setStartTime(startTime);
        expectedDto.setEndTime(endTime);
        expectedDto.setDate(date);
        expectedDto.setCompanyUuid(companyUuid);
        expectedDto.setIsSynced(isSynced);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedBy(updatedBy);
        expectedDto.setUpdatedAt(updatedAt);
        expectedDto.setDeleted(deleted);
        expectedDto.setDeletedAt(deletedAt);
        expectedDto.setChangeId(changeId);

        EquipmentUsageResponseDto actualDto = mapper.toDto(entity);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}

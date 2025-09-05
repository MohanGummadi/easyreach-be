package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.dto.diesel_usage.DieselUsageResponseDto;
import com.easyreach.backend.entity.DieselUsage;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DieselUsageMapperTest {

    private final DieselUsageMapper mapper = Mappers.getMapper(DieselUsageMapper.class);

    @Test
    void toEntity_and_toDto() {
        String dieselUsageId = "du1";
        String vehicleName = "Truck";
        OffsetDateTime date = OffsetDateTime.parse("2023-07-01T00:00:00Z");
        BigDecimal liters = new BigDecimal("40.0");
        String companyUuid = "comp1";
        Boolean isSynced = true;
        String createdBy = "creator";
        OffsetDateTime createdAt = OffsetDateTime.parse("2023-07-02T00:00:00Z");
        String updatedBy = "upd";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-07-03T00:00:00Z");
        Boolean deleted = true;
        OffsetDateTime deletedAt = OffsetDateTime.parse("2023-07-04T00:00:00Z");
        Long changeId = 8L;

        DieselUsageRequestDto dto = new DieselUsageRequestDto();
        dto.setDieselUsageId(dieselUsageId);
        dto.setVehicleName(vehicleName);
        dto.setDate(date);
        dto.setLiters(liters);
        dto.setCompanyUuid(companyUuid);
        dto.setIsSynced(isSynced);
        dto.setCreatedBy(createdBy);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedBy(updatedBy);
        dto.setUpdatedAt(updatedAt);
        dto.setDeleted(deleted);
        dto.setDeletedAt(deletedAt);
        dto.setChangeId(changeId);

        DieselUsage expectedEntity = DieselUsage.builder()
                .dieselUsageId(dieselUsageId)
                .vehicleName(vehicleName)
                .date(date)
                .liters(liters)
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

        DieselUsage entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        DieselUsageResponseDto expectedDto = new DieselUsageResponseDto();
        expectedDto.setDieselUsageId(dieselUsageId);
        expectedDto.setVehicleName(vehicleName);
        expectedDto.setDate(date);
        expectedDto.setLiters(liters);
        expectedDto.setCompanyUuid(companyUuid);
        expectedDto.setIsSynced(isSynced);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedBy(updatedBy);
        expectedDto.setUpdatedAt(updatedAt);
        expectedDto.setDeleted(deleted);
        expectedDto.setDeletedAt(deletedAt);
        expectedDto.setChangeId(changeId);

        DieselUsageResponseDto actualDto = mapper.toDto(entity);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}

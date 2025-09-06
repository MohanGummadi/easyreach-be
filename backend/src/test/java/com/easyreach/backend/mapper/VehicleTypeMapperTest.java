package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.vehicle_types.VehicleTypeRequestDto;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeResponseDto;
import com.easyreach.backend.entity.VehicleType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class VehicleTypeMapperTest {

    private final VehicleTypeMapper mapper = Mappers.getMapper(VehicleTypeMapper.class);

    @Test
    void toEntity_and_toDto() {
        String id = "vt1";
        String vehicleType = "Truck";
        String type = "Heavy";
        String companyUuid = "comp1";
        Boolean isSynced = true;
        String createdBy = "creator";
        OffsetDateTime createdAt = OffsetDateTime.parse("2023-02-01T00:00:00Z");
        String updatedBy = "upd";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-02-02T00:00:00Z");
        Boolean deleted = true;
        OffsetDateTime deletedAt = OffsetDateTime.parse("2023-02-03T00:00:00Z");
        Long changeId = 10L;

        VehicleTypeRequestDto dto = new VehicleTypeRequestDto();
        dto.setId(id);
        dto.setVehicleType(vehicleType);
        dto.setType(type);
        dto.setCompanyUuid(companyUuid);
        dto.setIsSynced(isSynced);
        dto.setCreatedBy(createdBy);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedBy(updatedBy);
        dto.setUpdatedAt(updatedAt);
        dto.setDeleted(deleted);
        dto.setDeletedAt(deletedAt);
        dto.setChangeId(changeId);

        VehicleType expectedEntity = VehicleType.builder()
                .id(id)
                .vehicleType(vehicleType)
                .type(type)
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

        VehicleType entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        VehicleTypeResponseDto expectedDto = new VehicleTypeResponseDto();
        expectedDto.setId(id);
        expectedDto.setVehicleType(vehicleType);
        expectedDto.setType(type);
        expectedDto.setCompanyUuid(companyUuid);
        expectedDto.setIsSynced(isSynced);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedBy(updatedBy);
        expectedDto.setUpdatedAt(updatedAt);
        expectedDto.setDeleted(deleted);
        expectedDto.setDeletedAt(deletedAt);
        expectedDto.setChangeId(changeId);

        VehicleTypeResponseDto actualDto = mapper.toDto(expectedEntity);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}


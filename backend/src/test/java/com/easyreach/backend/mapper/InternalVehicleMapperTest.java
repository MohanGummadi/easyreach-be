package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.internal_vehicles.InternalVehicleRequestDto;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleResponseDto;
import com.easyreach.backend.entity.InternalVehicle;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InternalVehicleMapperTest {

    private final InternalVehicleMapper mapper = Mappers.getMapper(InternalVehicleMapper.class);

    @Test
    void toEntity_and_toDto() {
        String vehicleId = "iv1";
        String vehicleName = "Loader";
        String vehicleType = "TypeA";
        Boolean isActive = true;
        String companyUuid = "comp1";
        Boolean isSynced = true;
        String createdBy = "creator";
        OffsetDateTime createdAt = OffsetDateTime.parse("2023-08-01T00:00:00Z");
        String updatedBy = "upd";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-08-02T00:00:00Z");
        Boolean deleted = true;
        OffsetDateTime deletedAt = OffsetDateTime.parse("2023-08-03T00:00:00Z");
        Long changeId = 9L;

        InternalVehicleRequestDto dto = new InternalVehicleRequestDto();
        dto.setVehicleId(vehicleId);
        dto.setVehicleName(vehicleName);
        dto.setVehicleType(vehicleType);
        dto.setIsActive(isActive);
        dto.setCompanyUuid(companyUuid);
        dto.setIsSynced(isSynced);
        dto.setCreatedBy(createdBy);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedBy(updatedBy);
        dto.setUpdatedAt(updatedAt);
        dto.setDeleted(deleted);
        dto.setDeletedAt(deletedAt);
        dto.setChangeId(changeId);

        InternalVehicle expectedEntity = InternalVehicle.builder()
                .vehicleId(vehicleId)
                .vehicleName(vehicleName)
                .vehicleType(vehicleType)
                .isActive(isActive)
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

        InternalVehicle entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        InternalVehicleResponseDto expectedDto = new InternalVehicleResponseDto();
        expectedDto.setVehicleId(vehicleId);
        expectedDto.setVehicleName(vehicleName);
        expectedDto.setVehicleType(vehicleType);
        expectedDto.setIsActive(isActive);
        expectedDto.setCompanyUuid(companyUuid);
        expectedDto.setIsSynced(isSynced);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedBy(updatedBy);
        expectedDto.setUpdatedAt(updatedAt);
        expectedDto.setDeleted(deleted);
        expectedDto.setDeletedAt(deletedAt);
        expectedDto.setChangeId(changeId);

        InternalVehicleResponseDto actualDto = mapper.toDto(entity);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}

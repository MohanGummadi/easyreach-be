package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.internal_vehicles.InternalVehicleRequestDto;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleResponseDto;
import com.easyreach.backend.entity.InternalVehicle;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:20+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class InternalVehicleMapperImpl implements InternalVehicleMapper {

    @Override
    public InternalVehicle toEntity(InternalVehicleRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        InternalVehicle.InternalVehicleBuilder internalVehicle = InternalVehicle.builder();

        internalVehicle.vehicleId( dto.getVehicleId() );
        internalVehicle.vehicleName( dto.getVehicleName() );
        internalVehicle.vehicleType( dto.getVehicleType() );
        internalVehicle.isActive( dto.getIsActive() );
        internalVehicle.companyUuid( dto.getCompanyUuid() );
        internalVehicle.isSynced( dto.getIsSynced() );
        internalVehicle.createdBy( dto.getCreatedBy() );
        internalVehicle.createdAt( dto.getCreatedAt() );
        internalVehicle.updatedBy( dto.getUpdatedBy() );
        internalVehicle.updatedAt( dto.getUpdatedAt() );

        return internalVehicle.build();
    }

    @Override
    public InternalVehicleResponseDto toDto(InternalVehicle entity) {
        if ( entity == null ) {
            return null;
        }

        InternalVehicleResponseDto internalVehicleResponseDto = new InternalVehicleResponseDto();

        internalVehicleResponseDto.setVehicleId( entity.getVehicleId() );
        internalVehicleResponseDto.setVehicleName( entity.getVehicleName() );
        internalVehicleResponseDto.setVehicleType( entity.getVehicleType() );
        internalVehicleResponseDto.setIsActive( entity.getIsActive() );
        internalVehicleResponseDto.setCompanyUuid( entity.getCompanyUuid() );
        internalVehicleResponseDto.setIsSynced( entity.getIsSynced() );
        internalVehicleResponseDto.setCreatedBy( entity.getCreatedBy() );
        internalVehicleResponseDto.setCreatedAt( entity.getCreatedAt() );
        internalVehicleResponseDto.setUpdatedBy( entity.getUpdatedBy() );
        internalVehicleResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return internalVehicleResponseDto;
    }

    @Override
    public void update(InternalVehicle entity, InternalVehicleRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getVehicleId() != null ) {
            entity.setVehicleId( dto.getVehicleId() );
        }
        if ( dto.getVehicleName() != null ) {
            entity.setVehicleName( dto.getVehicleName() );
        }
        if ( dto.getVehicleType() != null ) {
            entity.setVehicleType( dto.getVehicleType() );
        }
        if ( dto.getIsActive() != null ) {
            entity.setIsActive( dto.getIsActive() );
        }
        if ( dto.getCompanyUuid() != null ) {
            entity.setCompanyUuid( dto.getCompanyUuid() );
        }
        if ( dto.getIsSynced() != null ) {
            entity.setIsSynced( dto.getIsSynced() );
        }
        if ( dto.getCreatedBy() != null ) {
            entity.setCreatedBy( dto.getCreatedBy() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getUpdatedBy() != null ) {
            entity.setUpdatedBy( dto.getUpdatedBy() );
        }
        if ( dto.getUpdatedAt() != null ) {
            entity.setUpdatedAt( dto.getUpdatedAt() );
        }
    }
}

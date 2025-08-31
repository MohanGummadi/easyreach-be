package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.vehicle_types.VehicleTypeRequestDto;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeResponseDto;
import com.easyreach.backend.entity.VehicleType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:20+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class VehicleTypeMapperImpl implements VehicleTypeMapper {

    @Override
    public VehicleType toEntity(VehicleTypeRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        VehicleType.VehicleTypeBuilder vehicleType = VehicleType.builder();

        vehicleType.id( dto.getId() );
        vehicleType.vehicleType( dto.getVehicleType() );
        vehicleType.type( dto.getType() );
        vehicleType.companyUuid( dto.getCompanyUuid() );
        vehicleType.isSynced( dto.getIsSynced() );
        vehicleType.createdBy( dto.getCreatedBy() );
        vehicleType.createdAt( dto.getCreatedAt() );
        vehicleType.updatedBy( dto.getUpdatedBy() );
        vehicleType.updatedAt( dto.getUpdatedAt() );

        return vehicleType.build();
    }

    @Override
    public VehicleTypeResponseDto toDto(VehicleType entity) {
        if ( entity == null ) {
            return null;
        }

        VehicleTypeResponseDto vehicleTypeResponseDto = new VehicleTypeResponseDto();

        vehicleTypeResponseDto.setId( entity.getId() );
        vehicleTypeResponseDto.setVehicleType( entity.getVehicleType() );
        vehicleTypeResponseDto.setType( entity.getType() );
        vehicleTypeResponseDto.setCompanyUuid( entity.getCompanyUuid() );
        vehicleTypeResponseDto.setIsSynced( entity.getIsSynced() );
        vehicleTypeResponseDto.setCreatedBy( entity.getCreatedBy() );
        vehicleTypeResponseDto.setCreatedAt( entity.getCreatedAt() );
        vehicleTypeResponseDto.setUpdatedBy( entity.getUpdatedBy() );
        vehicleTypeResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return vehicleTypeResponseDto;
    }

    @Override
    public void update(VehicleType entity, VehicleTypeRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getVehicleType() != null ) {
            entity.setVehicleType( dto.getVehicleType() );
        }
        if ( dto.getType() != null ) {
            entity.setType( dto.getType() );
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

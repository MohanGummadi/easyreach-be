package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.dto.diesel_usage.DieselUsageResponseDto;
import com.easyreach.backend.entity.DieselUsage;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:20+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class DieselUsageMapperImpl implements DieselUsageMapper {

    @Override
    public DieselUsage toEntity(DieselUsageRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        DieselUsage.DieselUsageBuilder dieselUsage = DieselUsage.builder();

        dieselUsage.dieselUsageId( dto.getDieselUsageId() );
        dieselUsage.vehicleName( dto.getVehicleName() );
        dieselUsage.date( dto.getDate() );
        dieselUsage.liters( dto.getLiters() );
        dieselUsage.companyUuid( dto.getCompanyUuid() );
        dieselUsage.isSynced( dto.getIsSynced() );
        dieselUsage.createdBy( dto.getCreatedBy() );
        dieselUsage.createdAt( dto.getCreatedAt() );
        dieselUsage.updatedBy( dto.getUpdatedBy() );
        dieselUsage.updatedAt( dto.getUpdatedAt() );

        return dieselUsage.build();
    }

    @Override
    public DieselUsageResponseDto toDto(DieselUsage entity) {
        if ( entity == null ) {
            return null;
        }

        DieselUsageResponseDto dieselUsageResponseDto = new DieselUsageResponseDto();

        dieselUsageResponseDto.setDieselUsageId( entity.getDieselUsageId() );
        dieselUsageResponseDto.setVehicleName( entity.getVehicleName() );
        dieselUsageResponseDto.setDate( entity.getDate() );
        dieselUsageResponseDto.setLiters( entity.getLiters() );
        dieselUsageResponseDto.setCompanyUuid( entity.getCompanyUuid() );
        dieselUsageResponseDto.setIsSynced( entity.getIsSynced() );
        dieselUsageResponseDto.setCreatedBy( entity.getCreatedBy() );
        dieselUsageResponseDto.setCreatedAt( entity.getCreatedAt() );
        dieselUsageResponseDto.setUpdatedBy( entity.getUpdatedBy() );
        dieselUsageResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return dieselUsageResponseDto;
    }

    @Override
    public void update(DieselUsage entity, DieselUsageRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getDieselUsageId() != null ) {
            entity.setDieselUsageId( dto.getDieselUsageId() );
        }
        if ( dto.getVehicleName() != null ) {
            entity.setVehicleName( dto.getVehicleName() );
        }
        if ( dto.getDate() != null ) {
            entity.setDate( dto.getDate() );
        }
        if ( dto.getLiters() != null ) {
            entity.setLiters( dto.getLiters() );
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

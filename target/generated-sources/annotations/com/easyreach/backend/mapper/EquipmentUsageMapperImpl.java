package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.equipment_usage.EquipmentUsageRequestDto;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageResponseDto;
import com.easyreach.backend.entity.EquipmentUsage;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:19+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class EquipmentUsageMapperImpl implements EquipmentUsageMapper {

    @Override
    public EquipmentUsage toEntity(EquipmentUsageRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        EquipmentUsage.EquipmentUsageBuilder equipmentUsage = EquipmentUsage.builder();

        equipmentUsage.equipmentUsageId( dto.getEquipmentUsageId() );
        equipmentUsage.equipmentName( dto.getEquipmentName() );
        equipmentUsage.equipmentType( dto.getEquipmentType() );
        equipmentUsage.startKm( dto.getStartKm() );
        equipmentUsage.endKm( dto.getEndKm() );
        equipmentUsage.startTime( dto.getStartTime() );
        equipmentUsage.endTime( dto.getEndTime() );
        equipmentUsage.date( dto.getDate() );
        equipmentUsage.companyUuid( dto.getCompanyUuid() );
        equipmentUsage.isSynced( dto.getIsSynced() );
        equipmentUsage.createdBy( dto.getCreatedBy() );
        equipmentUsage.createdAt( dto.getCreatedAt() );
        equipmentUsage.updatedBy( dto.getUpdatedBy() );
        equipmentUsage.updatedAt( dto.getUpdatedAt() );

        return equipmentUsage.build();
    }

    @Override
    public EquipmentUsageResponseDto toDto(EquipmentUsage entity) {
        if ( entity == null ) {
            return null;
        }

        EquipmentUsageResponseDto equipmentUsageResponseDto = new EquipmentUsageResponseDto();

        equipmentUsageResponseDto.setEquipmentUsageId( entity.getEquipmentUsageId() );
        equipmentUsageResponseDto.setEquipmentName( entity.getEquipmentName() );
        equipmentUsageResponseDto.setEquipmentType( entity.getEquipmentType() );
        equipmentUsageResponseDto.setStartKm( entity.getStartKm() );
        equipmentUsageResponseDto.setEndKm( entity.getEndKm() );
        equipmentUsageResponseDto.setStartTime( entity.getStartTime() );
        equipmentUsageResponseDto.setEndTime( entity.getEndTime() );
        equipmentUsageResponseDto.setDate( entity.getDate() );
        equipmentUsageResponseDto.setCompanyUuid( entity.getCompanyUuid() );
        equipmentUsageResponseDto.setIsSynced( entity.getIsSynced() );
        equipmentUsageResponseDto.setCreatedBy( entity.getCreatedBy() );
        equipmentUsageResponseDto.setCreatedAt( entity.getCreatedAt() );
        equipmentUsageResponseDto.setUpdatedBy( entity.getUpdatedBy() );
        equipmentUsageResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return equipmentUsageResponseDto;
    }

    @Override
    public void update(EquipmentUsage entity, EquipmentUsageRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getEquipmentUsageId() != null ) {
            entity.setEquipmentUsageId( dto.getEquipmentUsageId() );
        }
        if ( dto.getEquipmentName() != null ) {
            entity.setEquipmentName( dto.getEquipmentName() );
        }
        if ( dto.getEquipmentType() != null ) {
            entity.setEquipmentType( dto.getEquipmentType() );
        }
        if ( dto.getStartKm() != null ) {
            entity.setStartKm( dto.getStartKm() );
        }
        if ( dto.getEndKm() != null ) {
            entity.setEndKm( dto.getEndKm() );
        }
        if ( dto.getStartTime() != null ) {
            entity.setStartTime( dto.getStartTime() );
        }
        if ( dto.getEndTime() != null ) {
            entity.setEndTime( dto.getEndTime() );
        }
        if ( dto.getDate() != null ) {
            entity.setDate( dto.getDate() );
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

package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.EquipmentUsage;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageRequestDto;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageResponseDto;

@Mapper(componentModel = "spring")
public interface EquipmentUsageMapper {
    EquipmentUsage toEntity(EquipmentUsageRequestDto dto);
    EquipmentUsageResponseDto toDto(EquipmentUsage entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget EquipmentUsage entity, EquipmentUsageRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(EquipmentUsageRequestDto dto, @MappingTarget EquipmentUsage entity);
}

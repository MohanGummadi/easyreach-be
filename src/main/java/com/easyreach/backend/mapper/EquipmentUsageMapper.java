package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.EquipmentUsage;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageRequestDto;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageResponseDto;

@Mapper(componentModel = "spring")
public interface EquipmentUsageMapper {
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    EquipmentUsage toEntity(EquipmentUsageRequestDto dto);

    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    EquipmentUsageResponseDto toDto(EquipmentUsage entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget EquipmentUsage entity, EquipmentUsageRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(EquipmentUsageRequestDto dto, @MappingTarget EquipmentUsage entity);
}

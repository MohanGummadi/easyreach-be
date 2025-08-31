package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.DieselUsage;
import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.dto.diesel_usage.DieselUsageResponseDto;

@Mapper(componentModel = "spring")
public interface DieselUsageMapper {
    DieselUsage toEntity(DieselUsageRequestDto dto);
    DieselUsageResponseDto toDto(DieselUsage entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget DieselUsage entity, DieselUsageRequestDto dto);
}

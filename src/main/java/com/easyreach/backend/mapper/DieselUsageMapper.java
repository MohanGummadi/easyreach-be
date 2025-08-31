package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.DieselUsage;
import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.dto.diesel_usage.DieselUsageResponseDto;

@Mapper(componentModel = "spring")
public interface DieselUsageMapper {
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    DieselUsage toEntity(DieselUsageRequestDto dto);

    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    DieselUsageResponseDto toDto(DieselUsage entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget DieselUsage entity, DieselUsageRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(DieselUsageRequestDto dto, @MappingTarget DieselUsage entity);
}

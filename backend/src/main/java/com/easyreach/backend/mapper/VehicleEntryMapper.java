package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.VehicleEntry;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;

@Mapper(componentModel = "spring")
public interface VehicleEntryMapper {
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    VehicleEntry toEntity(VehicleEntryRequestDto dto);

    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    VehicleEntryResponseDto toDto(VehicleEntry entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget VehicleEntry entity, VehicleEntryRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(VehicleEntryRequestDto dto, @MappingTarget VehicleEntry entity);
}

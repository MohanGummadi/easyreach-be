package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.VehicleEntry;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;

@Mapper(componentModel = "spring")
public interface VehicleEntryMapper {
    VehicleEntry toEntity(VehicleEntryRequestDto dto);
    VehicleEntryResponseDto toDto(VehicleEntry entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget VehicleEntry entity, VehicleEntryRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(VehicleEntryRequestDto dto, @MappingTarget VehicleEntry entity);
}

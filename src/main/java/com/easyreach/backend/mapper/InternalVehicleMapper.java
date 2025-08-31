package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.InternalVehicle;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleRequestDto;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleResponseDto;

@Mapper(componentModel = "spring")
public interface InternalVehicleMapper {
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    InternalVehicle toEntity(InternalVehicleRequestDto dto);

    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    InternalVehicleResponseDto toDto(InternalVehicle entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget InternalVehicle entity, InternalVehicleRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(InternalVehicleRequestDto dto, @MappingTarget InternalVehicle entity);
}

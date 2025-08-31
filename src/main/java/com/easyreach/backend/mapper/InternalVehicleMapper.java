package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.InternalVehicle;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleRequestDto;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleResponseDto;

@Mapper(componentModel = "spring")
public interface InternalVehicleMapper {
    InternalVehicle toEntity(InternalVehicleRequestDto dto);
    InternalVehicleResponseDto toDto(InternalVehicle entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget InternalVehicle entity, InternalVehicleRequestDto dto);
}

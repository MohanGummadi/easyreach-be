package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.VehicleType;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeRequestDto;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeResponseDto;

@Mapper(componentModel = "spring")
public interface VehicleTypeMapper {
    VehicleType toEntity(VehicleTypeRequestDto dto);
    VehicleTypeResponseDto toDto(VehicleType entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget VehicleType entity, VehicleTypeRequestDto dto);
}

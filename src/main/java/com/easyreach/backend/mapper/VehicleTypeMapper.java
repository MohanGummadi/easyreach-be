package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.VehicleType;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeRequestDto;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeResponseDto;

@Mapper(componentModel = "spring")
public interface VehicleTypeMapper {
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    VehicleType toEntity(VehicleTypeRequestDto dto);

    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    VehicleTypeResponseDto toDto(VehicleType entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget VehicleType entity, VehicleTypeRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(VehicleTypeRequestDto dto, @MappingTarget VehicleType entity);
}

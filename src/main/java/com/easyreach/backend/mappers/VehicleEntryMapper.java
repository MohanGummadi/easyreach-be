package com.easyreach.backend.mappers;

import com.easyreach.backend.dto.VehicleEntryDto;
import com.easyreach.backend.entities.VehicleEntry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleEntryMapper {
    VehicleEntry toEntity(VehicleEntryDto dto);
    VehicleEntryDto toDto(VehicleEntry entity);
}

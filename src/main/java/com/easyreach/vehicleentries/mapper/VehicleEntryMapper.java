package com.easyreach.vehicleentries.mapper;

import com.easyreach.vehicleentries.dto.VehicleEntryDto;
import com.easyreach.vehicleentries.entity.VehicleEntry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleEntryMapper {
    VehicleEntry toEntity(VehicleEntryDto dto);
    VehicleEntryDto toDto(VehicleEntry entity);
}

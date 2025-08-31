package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleRequestDto;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InternalVehicleService {
    ApiResponse<InternalVehicleResponseDto> create(InternalVehicleRequestDto dto);
    ApiResponse<InternalVehicleResponseDto> update(String id, InternalVehicleRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<InternalVehicleResponseDto> get(String id);
    ApiResponse<Page<InternalVehicleResponseDto>> list(Pageable pageable);
}

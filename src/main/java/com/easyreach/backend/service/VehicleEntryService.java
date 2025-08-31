package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleEntryService {
    ApiResponse<VehicleEntryResponseDto> create(VehicleEntryRequestDto dto);
    ApiResponse<VehicleEntryResponseDto> update(String id, VehicleEntryRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<VehicleEntryResponseDto> get(String id);
    ApiResponse<Page<VehicleEntryResponseDto>> list(Pageable pageable);
}

package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.time.OffsetDateTime;

public interface VehicleEntryService {
    ApiResponse<VehicleEntryResponseDto> create(VehicleEntryRequestDto dto);
    ApiResponse<VehicleEntryResponseDto> update(String id, VehicleEntryRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<VehicleEntryResponseDto> get(String id);
    ApiResponse<Page<VehicleEntryResponseDto>> list(Pageable pageable);
    int bulkSync(List<VehicleEntryRequestDto> dtos);
    Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit);
}

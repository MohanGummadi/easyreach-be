package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeRequestDto;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.time.OffsetDateTime;

public interface VehicleTypeService {
    ApiResponse<VehicleTypeResponseDto> create(VehicleTypeRequestDto dto);
    ApiResponse<VehicleTypeResponseDto> update(String id, VehicleTypeRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<VehicleTypeResponseDto> get(String id);
    ApiResponse<Page<VehicleTypeResponseDto>> list(Pageable pageable);
    int bulkSync(List<VehicleTypeRequestDto> dtos);
    Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit);
}

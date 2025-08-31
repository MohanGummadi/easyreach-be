package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageRequestDto;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EquipmentUsageService {
    ApiResponse<EquipmentUsageResponseDto> create(EquipmentUsageRequestDto dto);
    ApiResponse<EquipmentUsageResponseDto> update(String id, EquipmentUsageRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<EquipmentUsageResponseDto> get(String id);
    ApiResponse<Page<EquipmentUsageResponseDto>> list(Pageable pageable);
}

package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.dto.diesel_usage.DieselUsageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.time.OffsetDateTime;

public interface DieselUsageService {
    ApiResponse<DieselUsageResponseDto> create(DieselUsageRequestDto dto);
    ApiResponse<DieselUsageResponseDto> update(String id, DieselUsageRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<DieselUsageResponseDto> get(String id);
    ApiResponse<Page<DieselUsageResponseDto>> list(Pageable pageable);
    int bulkSync(List<DieselUsageRequestDto> dtos);
    Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit);
}

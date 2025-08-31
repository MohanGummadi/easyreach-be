package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.time.OffsetDateTime;

public interface CompanyService {
    ApiResponse<CompanyResponseDto> create(CompanyRequestDto dto);
    ApiResponse<CompanyResponseDto> update(String id, CompanyRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<CompanyResponseDto> get(String id);
    ApiResponse<Page<CompanyResponseDto>> list(Pageable pageable);
    int bulkSync(List<CompanyRequestDto> dtos);
    Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit);
}

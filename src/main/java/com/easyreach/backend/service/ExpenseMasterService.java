package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.expense_master.ExpenseMasterRequestDto;
import com.easyreach.backend.dto.expense_master.ExpenseMasterResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.time.OffsetDateTime;

public interface ExpenseMasterService {
    ApiResponse<ExpenseMasterResponseDto> create(ExpenseMasterRequestDto dto);
    ApiResponse<ExpenseMasterResponseDto> update(String id, ExpenseMasterRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<ExpenseMasterResponseDto> get(String id);
    ApiResponse<Page<ExpenseMasterResponseDto>> list(Pageable pageable);
    int bulkSync(List<ExpenseMasterRequestDto> dtos);
    Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit);
}

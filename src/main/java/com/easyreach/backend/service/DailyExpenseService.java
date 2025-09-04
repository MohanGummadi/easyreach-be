package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseRequestDto;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.time.OffsetDateTime;
import java.util.Map;
import java.time.OffsetDateTime;

public interface DailyExpenseService {
    ApiResponse<DailyExpenseResponseDto> create(DailyExpenseRequestDto dto);
    ApiResponse<DailyExpenseResponseDto> update(String id, DailyExpenseRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<DailyExpenseResponseDto> get(String id);
    ApiResponse<Page<DailyExpenseResponseDto>> list(Pageable pageable, OffsetDateTime dateFrom, OffsetDateTime dateTo);
    int bulkSync(List<DailyExpenseRequestDto> dtos);
    Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit);
}

package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.time.OffsetDateTime;

public interface PayerService {
    ApiResponse<PayerResponseDto> create(PayerRequestDto dto);
    ApiResponse<PayerResponseDto> update(String id, PayerRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<PayerResponseDto> get(String id);
    ApiResponse<Page<PayerResponseDto>> list(Pageable pageable);
    int bulkSync(List<PayerRequestDto> dtos);
    Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit);
}

package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementResponseDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementWithNameDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.OffsetDateTime;

public interface PayerSettlementService {
    ApiResponse<PayerSettlementResponseDto> create(PayerSettlementRequestDto dto);
    ApiResponse<PayerSettlementResponseDto> update(String id, PayerSettlementRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<PayerSettlementResponseDto> get(String id);
    ApiResponse<Page<PayerSettlementResponseDto>> list(Pageable pageable);
    int bulkSync(List<PayerSettlementRequestDto> dtos);
    ApiResponse<List<PayerSettlementResponseDto>> getSettlements(String payerId, Optional<String> companyUuid);
    ApiResponse<List<PayerSettlementResponseDto>> getAllSettlements(Optional<String> companyUuid);
    ApiResponse<List<PayerSettlementWithNameDto>> getByCompanyWithPayerName(String companyUuid);
    ApiResponse<List<PayerSettlementWithNameDto>> getByPayerWithName(String payerId);
    Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit);
}

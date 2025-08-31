package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayerSettlementService {
    ApiResponse<PayerSettlementResponseDto> create(PayerSettlementRequestDto dto);
    ApiResponse<PayerSettlementResponseDto> update(String id, PayerSettlementRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<PayerSettlementResponseDto> get(String id);
    ApiResponse<Page<PayerSettlementResponseDto>> list(Pageable pageable);
}

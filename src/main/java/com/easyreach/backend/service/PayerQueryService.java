package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayerQueryService {
    ApiResponse<Page<PayerResponseDto>> searchActive(String companyUuid, String q, Pageable pageable);
    ApiResponse<Void> softDelete(String payerId);
}

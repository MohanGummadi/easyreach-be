package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.entity.Payer;
import com.easyreach.backend.mapper.PayerMapper;
import com.easyreach.backend.repository.PayerRepository;
import com.easyreach.backend.service.PayerQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PayerQueryServiceImpl extends CompanyScopedService implements PayerQueryService {

    private final PayerRepository repository;
    private final PayerMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<PayerResponseDto>> searchActive(String companyUuid, String q, Pageable pageable) {
        Page<Payer> page = (q == null || q.isBlank())
                ? repository.findByCompanyUuidAndDeletedAtIsNull(companyUuid, pageable)
                : repository.findByCompanyUuidAndPayerNameContainingIgnoreCaseAndDeletedAtIsNull(companyUuid, q, pageable);
        return ApiResponse.success(page.map(mapper::toDto));
    }

    @Override
    public ApiResponse<Void> softDelete(String payerId) {
        Payer p = repository.findByPayerIdAndCompanyUuidAndDeletedIsFalse(payerId, currentCompany()).orElseThrow(() -> new EntityNotFoundException("Payer not found: " + payerId));
        p.setDeletedAt(OffsetDateTime.now());
        repository.save(p);
        return ApiResponse.success(null);
    }
}

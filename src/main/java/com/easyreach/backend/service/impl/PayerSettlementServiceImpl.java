package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementResponseDto;
import com.easyreach.backend.entity.PayerSettlement;
import com.easyreach.backend.mapper.PayerSettlementMapper;
import com.easyreach.backend.repository.PayerSettlementRepository;
import com.easyreach.backend.service.PayerSettlementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PayerSettlementServiceImpl implements PayerSettlementService {
    private final PayerSettlementRepository repository;
    private final PayerSettlementMapper mapper;

    @Override
    public ApiResponse<PayerSettlementResponseDto> create(PayerSettlementRequestDto dto) {
        PayerSettlement entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<PayerSettlementResponseDto> update(String id, PayerSettlementRequestDto dto) {
        PayerSettlement e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("PayerSettlement not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("PayerSettlement not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<PayerSettlementResponseDto> get(String id) {
        PayerSettlement e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("PayerSettlement not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<PayerSettlementResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
    }
}

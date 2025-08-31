package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.entity.Payer;
import com.easyreach.backend.mapper.PayerMapper;
import com.easyreach.backend.repository.PayerRepository;
import com.easyreach.backend.service.PayerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PayerServiceImpl implements PayerService {
    private final PayerRepository repository;
    private final PayerMapper mapper;

    @Override
    public ApiResponse<PayerResponseDto> create(PayerRequestDto dto) {
        Payer entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<PayerResponseDto> update(String id, PayerRequestDto dto) {
        Payer e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payer not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Payer not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<PayerResponseDto> get(String id) {
        Payer e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payer not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<PayerResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<PayerRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        List<Payer> entities = dtos.stream()
                .map(mapper::toEntity)
                .peek(e -> e.setIsSynced(true))
                .toList();
        repository.saveAll(entities);
        return entities.size();
    }
}

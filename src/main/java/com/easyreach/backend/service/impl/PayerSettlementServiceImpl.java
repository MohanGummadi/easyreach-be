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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Override
    public int bulkSync(List<PayerSettlementRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        Map<String, PayerSettlementRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getSettlementId() != null)
                .collect(Collectors.toMap(PayerSettlementRequestDto::getSettlementId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) return 0;

        Map<String, PayerSettlement> existing = repository.findAllById(dtoMap.keySet()).stream()
                .collect(Collectors.toMap(PayerSettlement::getSettlementId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<PayerSettlement> entities = new ArrayList<>();
        for (PayerSettlementRequestDto dto : dtoMap.values()) {
            PayerSettlement entity = existing.get(dto.getSettlementId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setChangeId(entity.getChangeId() == null ? 0L : entity.getChangeId() + 1);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                PayerSettlement e = mapper.toEntity(dto);
                e.setCreatedAt(now);
                e.setUpdatedAt(now);
                e.setIsSynced(true);
                entities.add(e);
            }
        }
        repository.saveAll(entities);
        return entities.size();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit) {
        Map<String, Object> result = new HashMap<>();
        List<PayerSettlement> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(companyUuid, cursor, PageRequest.of(0, limit));
        result.put("updated", updates.stream().map(mapper::toDto).toList());
        int remaining = limit - updates.size();
        List<String> tombstones = remaining > 0
                ? repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(companyUuid, cursor, PageRequest.of(0, remaining))
                .stream().map(PayerSettlement::getSettlementId).toList()
                : Collections.emptyList();
        result.put("tombstones", tombstones);
        return result;
    }
}

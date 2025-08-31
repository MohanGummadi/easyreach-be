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
        PayerSettlement e = repository.findBySettlementIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("PayerSettlement not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        PayerSettlement e = repository.findBySettlementIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("PayerSettlement not found: " + id));
        e.setDeleted(true);
        e.setDeletedAt(OffsetDateTime.now());
        repository.save(e);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<PayerSettlementResponseDto> get(String id) {
        PayerSettlement e = repository.findBySettlementIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("PayerSettlement not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<PayerSettlementResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findByDeletedIsFalse(pageable).map(mapper::toDto));
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
        boolean hasMore = false;

        List<PayerSettlement> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(
                companyUuid, cursor, PageRequest.of(0, limit + 1));
        if (updates.size() > limit) {
            hasMore = true;
            updates = updates.subList(0, limit);
        }
        result.put("items", updates.stream().map(mapper::toDto).toList());

        int remaining = limit - updates.size();
        List<PayerSettlement> tombstoneEntities = Collections.emptyList();
        if (remaining > 0) {
            tombstoneEntities = repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(
                    companyUuid, cursor, PageRequest.of(0, remaining + 1));
            if (tombstoneEntities.size() > remaining) {
                hasMore = true;
                tombstoneEntities = tombstoneEntities.subList(0, remaining);
            }
        }
        result.put("tombstones", tombstoneEntities.stream().map(PayerSettlement::getSettlementId).toList());

        OffsetDateTime cursorEnd = cursor;
        for (PayerSettlement e : updates) {
            OffsetDateTime ts = e.getUpdatedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        for (PayerSettlement e : tombstoneEntities) {
            OffsetDateTime ts = e.getDeletedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        result.put("cursorEnd", cursorEnd);
        result.put("hasMore", hasMore);
        return result;
    }
}

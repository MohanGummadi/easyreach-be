package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseRequestDto;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseResponseDto;
import com.easyreach.backend.entity.DailyExpense;
import com.easyreach.backend.mapper.DailyExpenseMapper;
import com.easyreach.backend.repository.DailyExpenseRepository;
import com.easyreach.backend.service.DailyExpenseService;
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
public class DailyExpenseServiceImpl extends CompanyScopedService implements DailyExpenseService {
    private final DailyExpenseRepository repository;
    private final DailyExpenseMapper mapper;

    @Override
    public ApiResponse<DailyExpenseResponseDto> create(DailyExpenseRequestDto dto) {
        DailyExpense entity = mapper.toEntity(dto);
        entity.setCompanyUuid(currentCompany());
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<DailyExpenseResponseDto> update(String id, DailyExpenseRequestDto dto) {
        DailyExpense e = repository.findByExpenseIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> new EntityNotFoundException("DailyExpense not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        DailyExpense e = repository.findByExpenseIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> new EntityNotFoundException("DailyExpense not found: " + id));
        e.setDeleted(true);
        e.setDeletedAt(OffsetDateTime.now());
        repository.save(e);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<DailyExpenseResponseDto> get(String id) {
        DailyExpense e = repository.findByExpenseIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> new EntityNotFoundException("DailyExpense not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<DailyExpenseResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findByCompanyUuidAndDeletedIsFalse(currentCompany(), pageable).map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<DailyExpenseRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        Map<String, DailyExpenseRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getExpenseId() != null)
                .collect(Collectors.toMap(DailyExpenseRequestDto::getExpenseId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) return 0;

        Map<String, DailyExpense> existing = repository.findAllById(dtoMap.keySet()).stream()
                .collect(Collectors.toMap(DailyExpense::getExpenseId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<DailyExpense> entities = new ArrayList<>();
        for (DailyExpenseRequestDto dto : dtoMap.values()) {
            DailyExpense entity = existing.get(dto.getExpenseId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                DailyExpense e = mapper.toEntity(dto);
                e.setCompanyUuid(currentCompany());
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

        List<DailyExpense> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(
                companyUuid, cursor, PageRequest.of(0, limit + 1));
        if (updates.size() > limit) {
            hasMore = true;
            updates = updates.subList(0, limit);
        }
        result.put("items", updates.stream().map(mapper::toDto).toList());

        int remaining = limit - updates.size();
        List<DailyExpense> tombstoneEntities = Collections.emptyList();
        if (remaining > 0) {
            tombstoneEntities = repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(
                    companyUuid, cursor, PageRequest.of(0, remaining + 1));
            if (tombstoneEntities.size() > remaining) {
                hasMore = true;
                tombstoneEntities = tombstoneEntities.subList(0, remaining);
            }
        }
        result.put("tombstones", tombstoneEntities.stream().map(DailyExpense::getExpenseId).toList());

        OffsetDateTime cursorEnd = cursor;
        for (DailyExpense e : updates) {
            OffsetDateTime ts = e.getUpdatedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        for (DailyExpense e : tombstoneEntities) {
            OffsetDateTime ts = e.getDeletedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        result.put("cursorEnd", cursorEnd);
        result.put("hasMore", hasMore);
        return result;
    }
}

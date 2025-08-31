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
public class DailyExpenseServiceImpl implements DailyExpenseService {
    private final DailyExpenseRepository repository;
    private final DailyExpenseMapper mapper;

    @Override
    public ApiResponse<DailyExpenseResponseDto> create(DailyExpenseRequestDto dto) {
        DailyExpense entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<DailyExpenseResponseDto> update(String id, DailyExpenseRequestDto dto) {
        DailyExpense e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("DailyExpense not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("DailyExpense not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<DailyExpenseResponseDto> get(String id) {
        DailyExpense e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("DailyExpense not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<DailyExpenseResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
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
                entity.setChangeId(entity.getChangeId() == null ? 0L : entity.getChangeId() + 1);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                DailyExpense e = mapper.toEntity(dto);
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
        List<DailyExpense> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(companyUuid, cursor, PageRequest.of(0, limit));
        result.put("updated", updates.stream().map(mapper::toDto).toList());
        int remaining = limit - updates.size();
        List<String> tombstones = remaining > 0
                ? repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(companyUuid, cursor, PageRequest.of(0, remaining))
                .stream().map(DailyExpense::getExpenseId).toList()
                : Collections.emptyList();
        result.put("tombstones", tombstones);
        return result;
    }
}

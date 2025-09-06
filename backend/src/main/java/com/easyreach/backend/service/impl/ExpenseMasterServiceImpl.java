package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.expense_master.ExpenseMasterRequestDto;
import com.easyreach.backend.dto.expense_master.ExpenseMasterResponseDto;
import com.easyreach.backend.entity.ExpenseMaster;
import com.easyreach.backend.mapper.ExpenseMasterMapper;
import com.easyreach.backend.repository.ExpenseMasterRepository;
import com.easyreach.backend.service.ExpenseMasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ExpenseMasterServiceImpl extends CompanyScopedService implements ExpenseMasterService {
    private final ExpenseMasterRepository repository;
    private final ExpenseMasterMapper mapper;

    @Override
    public ApiResponse<ExpenseMasterResponseDto> create(ExpenseMasterRequestDto dto) {
        log.debug("Entering create with dto={}", dto);
        ExpenseMaster entity = mapper.toEntity(dto);
        entity.setCompanyUuid(currentCompany());
        ApiResponse<ExpenseMasterResponseDto> response = ApiResponse.success(mapper.toDto(repository.save(entity)));
        log.debug("Exiting create with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<ExpenseMasterResponseDto> update(String id, ExpenseMasterRequestDto dto) {
        log.debug("Entering update with id={} dto={}", id, dto);
        ExpenseMaster e = repository.findByIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("ExpenseMaster not found: {}", id);
                    return new EntityNotFoundException("ExpenseMaster not found: " + id);
                });
        mapper.update(e, dto);
        ApiResponse<ExpenseMasterResponseDto> response = ApiResponse.success(mapper.toDto(repository.save(e)));
        log.debug("Exiting update with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        log.debug("Entering delete with id={}", id);
        ExpenseMaster e = repository.findByIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("ExpenseMaster not found: {}", id);
                    return new EntityNotFoundException("ExpenseMaster not found: " + id);
                });
        e.setDeleted(true);
        e.setDeletedAt(OffsetDateTime.now());
        repository.save(e);
        ApiResponse<Void> response = ApiResponse.success(null);
        log.debug("Exiting delete with response={}", response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<ExpenseMasterResponseDto> get(String id) {
        log.debug("Entering get with id={}", id);
        ExpenseMaster e = repository.findByIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("ExpenseMaster not found: {}", id);
                    return new EntityNotFoundException("ExpenseMaster not found: " + id);
                });
        ApiResponse<ExpenseMasterResponseDto> response = ApiResponse.success(mapper.toDto(e));
        log.debug("Exiting get with response={}", response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<ExpenseMasterResponseDto>> list(Pageable pageable) {
        log.debug("Entering list with pageable={}", pageable);
        ApiResponse<Page<ExpenseMasterResponseDto>> response = ApiResponse.success(
                repository.findByCompanyUuidAndDeletedIsFalse(currentCompany(), pageable).map(mapper::toDto));
        log.debug("Exiting list with response={}", response);
        return response;
    }

    @Override
    public int bulkSync(List<ExpenseMasterRequestDto> dtos) {
        log.debug("Entering bulkSync with {} dtos", dtos != null ? dtos.size() : 0);
        if (dtos == null || dtos.isEmpty()) {
            log.warn("bulkSync called with empty dto list");
            return 0;
        }
        Map<String, ExpenseMasterRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getId() != null)
                .collect(Collectors.toMap(ExpenseMasterRequestDto::getId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) {
            log.warn("bulkSync dtoMap empty after filtering ids");
            return 0;
        }

        Map<String, ExpenseMaster> existing = repository.findByIdInAndCompanyUuid(dtoMap.keySet(), currentCompany()).stream()
                .collect(Collectors.toMap(ExpenseMaster::getId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<ExpenseMaster> entities = new ArrayList<>();
        for (ExpenseMasterRequestDto dto : dtoMap.values()) {
            ExpenseMaster entity = existing.get(dto.getId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                ExpenseMaster e = mapper.toEntity(dto);
                e.setCompanyUuid(currentCompany());
                e.setCreatedAt(now);
                e.setUpdatedAt(now);
                e.setIsSynced(true);
                entities.add(e);
            }
        }
        repository.saveAll(entities);
        int size = entities.size();
        log.debug("Exiting bulkSync with size={}", size);
        return size;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit) {
        log.debug("Entering fetchChangesSince companyUuid={} cursor={} limit={}", companyUuid, cursor, limit);
        Map<String, Object> result = new HashMap<>();
        boolean hasMore = false;

        List<ExpenseMaster> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(
                companyUuid, cursor, PageRequest.of(0, limit + 1));
        if (updates.size() > limit) {
            hasMore = true;
            updates = updates.subList(0, limit);
        }
        result.put("items", updates.stream().map(mapper::toDto).toList());

        int remaining = limit - updates.size();
        List<ExpenseMaster> tombstoneEntities = Collections.emptyList();
        if (remaining > 0) {
            tombstoneEntities = repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(
                    companyUuid, cursor, PageRequest.of(0, remaining + 1));
            if (tombstoneEntities.size() > remaining) {
                hasMore = true;
                tombstoneEntities = tombstoneEntities.subList(0, remaining);
            }
        }
        result.put("tombstones", tombstoneEntities.stream().map(ExpenseMaster::getId).toList());

        OffsetDateTime cursorEnd = cursor;
        for (ExpenseMaster e : updates) {
            OffsetDateTime ts = e.getUpdatedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        for (ExpenseMaster e : tombstoneEntities) {
            OffsetDateTime ts = e.getDeletedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        result.put("cursorEnd", cursorEnd);
        result.put("hasMore", hasMore);
        log.debug("Exiting fetchChangesSince cursorEnd={} hasMore={}", cursorEnd, hasMore);
        return result;
    }
}

package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.dto.diesel_usage.DieselUsageResponseDto;
import com.easyreach.backend.entity.DieselUsage;
import com.easyreach.backend.mapper.DieselUsageMapper;
import com.easyreach.backend.repository.DieselUsageRepository;
import com.easyreach.backend.service.DieselUsageService;
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
public class DieselUsageServiceImpl extends CompanyScopedService implements DieselUsageService {
    private final DieselUsageRepository repository;
    private final DieselUsageMapper mapper;

    @Override
    public ApiResponse<DieselUsageResponseDto> create(DieselUsageRequestDto dto) {
        log.debug("Entering create with dto={}", dto);
        DieselUsage entity = mapper.toEntity(dto);
        entity.setCompanyUuid(currentCompany());
        ApiResponse<DieselUsageResponseDto> response = ApiResponse.success(mapper.toDto(repository.save(entity)));
        log.debug("Exiting create with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<DieselUsageResponseDto> update(String id, DieselUsageRequestDto dto) {
        log.debug("Entering update with id={} dto={}", id, dto);
        DieselUsage e = repository.findByDieselUsageIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("DieselUsage not found: {}", id);
                    return new EntityNotFoundException("DieselUsage not found: " + id);
                });
        mapper.update(e, dto);
        ApiResponse<DieselUsageResponseDto> response = ApiResponse.success(mapper.toDto(repository.save(e)));
        log.debug("Exiting update with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        log.debug("Entering delete with id={}", id);
        DieselUsage e = repository.findByDieselUsageIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("DieselUsage not found: {}", id);
                    return new EntityNotFoundException("DieselUsage not found: " + id);
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
    public ApiResponse<DieselUsageResponseDto> get(String id) {
        log.debug("Entering get with id={}", id);
        DieselUsage e = repository.findByDieselUsageIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("DieselUsage not found: {}", id);
                    return new EntityNotFoundException("DieselUsage not found: " + id);
                });
        ApiResponse<DieselUsageResponseDto> response = ApiResponse.success(mapper.toDto(e));
        log.debug("Exiting get with response={}", response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<DieselUsageResponseDto>> list(Pageable pageable) {
        log.debug("Entering list with pageable={}", pageable);
        ApiResponse<Page<DieselUsageResponseDto>> response = ApiResponse.success(
                repository.findByCompanyUuidAndDeletedIsFalse(currentCompany(), pageable).map(mapper::toDto));
        log.debug("Exiting list with response={}", response);
        return response;
    }

    @Override
    public int bulkSync(List<DieselUsageRequestDto> dtos) {
        log.debug("Entering bulkSync with {} dtos", dtos != null ? dtos.size() : 0);
        if (dtos == null || dtos.isEmpty()) {
            log.warn("bulkSync called with empty dto list");
            return 0;
        }
        Map<String, DieselUsageRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getDieselUsageId() != null)
                .collect(Collectors.toMap(DieselUsageRequestDto::getDieselUsageId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) {
            log.warn("bulkSync dtoMap empty after filtering ids");
            return 0;
        }

        Map<String, DieselUsage> existing = repository.findAllById(dtoMap.keySet()).stream()
                .collect(Collectors.toMap(DieselUsage::getDieselUsageId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<DieselUsage> entities = new ArrayList<>();
        for (DieselUsageRequestDto dto : dtoMap.values()) {
            DieselUsage entity = existing.get(dto.getDieselUsageId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                DieselUsage e = mapper.toEntity(dto);
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

        List<DieselUsage> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(
                companyUuid, cursor, PageRequest.of(0, limit + 1));
        if (updates.size() > limit) {
            hasMore = true;
            updates = updates.subList(0, limit);
        }
        result.put("items", updates.stream().map(mapper::toDto).toList());

        int remaining = limit - updates.size();
        List<DieselUsage> tombstoneEntities = Collections.emptyList();
        if (remaining > 0) {
            tombstoneEntities = repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(
                    companyUuid, cursor, PageRequest.of(0, remaining + 1));
            if (tombstoneEntities.size() > remaining) {
                hasMore = true;
                tombstoneEntities = tombstoneEntities.subList(0, remaining);
            }
        }
        result.put("tombstones", tombstoneEntities.stream().map(DieselUsage::getDieselUsageId).toList());

        OffsetDateTime cursorEnd = cursor;
        for (DieselUsage e : updates) {
            OffsetDateTime ts = e.getUpdatedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        for (DieselUsage e : tombstoneEntities) {
            OffsetDateTime ts = e.getDeletedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        result.put("cursorEnd", cursorEnd);
        result.put("hasMore", hasMore);
        log.debug("Exiting fetchChangesSince cursorEnd={} hasMore={}", cursorEnd, hasMore);
        return result;
    }
}

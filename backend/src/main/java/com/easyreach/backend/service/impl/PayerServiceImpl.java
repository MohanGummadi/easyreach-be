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
public class PayerServiceImpl extends CompanyScopedService implements PayerService {
    private final PayerRepository repository;
    private final PayerMapper mapper;

    @Override
    public ApiResponse<PayerResponseDto> create(PayerRequestDto dto) {
        log.debug("Entering create with dto={}", dto);
        Payer entity = mapper.toEntity(dto);
        entity.setCompanyUuid(currentCompany());
        ApiResponse<PayerResponseDto> response = ApiResponse.success(mapper.toDto(repository.save(entity)));
        log.debug("Exiting create with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<PayerResponseDto> update(String id, PayerRequestDto dto) {
        log.debug("Entering update with id={} dto={}", id, dto);
        Payer e = repository.findByPayerIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("Payer not found: {}", id);
                    return new EntityNotFoundException("Payer not found: " + id);
                });
        mapper.update(e, dto);
        ApiResponse<PayerResponseDto> response = ApiResponse.success(mapper.toDto(repository.save(e)));
        log.debug("Exiting update with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        log.debug("Entering delete with id={}", id);
        Payer e = repository.findByPayerIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("Payer not found: {}", id);
                    return new EntityNotFoundException("Payer not found: " + id);
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
    public ApiResponse<PayerResponseDto> get(String id) {
        log.debug("Entering get with id={}", id);
        Payer e = repository.findByPayerIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("Payer not found: {}", id);
                    return new EntityNotFoundException("Payer not found: " + id);
                });
        ApiResponse<PayerResponseDto> response = ApiResponse.success(mapper.toDto(e));
        log.debug("Exiting get with response={}", response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<PayerResponseDto>> list(Pageable pageable) {
        log.debug("Entering list with pageable={}", pageable);
        ApiResponse<Page<PayerResponseDto>> response = ApiResponse.success(
                repository.findByCompanyUuidAndDeletedIsFalse(currentCompany(), pageable).map(mapper::toDto));
        log.debug("Exiting list with response={}", response);
        return response;
    }

    @Override
    public int bulkSync(List<PayerRequestDto> dtos) {
        log.debug("Entering bulkSync with {} dtos", dtos != null ? dtos.size() : 0);
        if (dtos == null || dtos.isEmpty()) {
            log.warn("bulkSync called with empty dto list");
            return 0;
        }
        Map<String, PayerRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getPayerId() != null)
                .collect(Collectors.toMap(PayerRequestDto::getPayerId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) {
            log.warn("bulkSync dtoMap empty after filtering ids");
            return 0;
        }

        Map<String, Payer> existing = repository.findByPayerIdInAndCompanyUuid(dtoMap.keySet(), currentCompany()).stream()
                .collect(Collectors.toMap(Payer::getPayerId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<Payer> entities = new ArrayList<>();
        for (PayerRequestDto dto : dtoMap.values()) {
            Payer entity = existing.get(dto.getPayerId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                Payer e = mapper.toEntity(dto);
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

        List<Payer> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(
                companyUuid, cursor, PageRequest.of(0, limit + 1));
        if (updates.size() > limit) {
            hasMore = true;
            updates = updates.subList(0, limit);
        }
        result.put("items", updates.stream().map(mapper::toDto).toList());

        int remaining = limit - updates.size();
        List<Payer> tombstoneEntities = Collections.emptyList();
        if (remaining > 0) {
            tombstoneEntities = repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(
                    companyUuid, cursor, PageRequest.of(0, remaining + 1));
            if (tombstoneEntities.size() > remaining) {
                hasMore = true;
                tombstoneEntities = tombstoneEntities.subList(0, remaining);
            }
        }
        result.put("tombstones", tombstoneEntities.stream().map(Payer::getPayerId).toList());

        OffsetDateTime cursorEnd = cursor;
        for (Payer e : updates) {
            OffsetDateTime ts = e.getUpdatedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        for (Payer e : tombstoneEntities) {
            OffsetDateTime ts = e.getDeletedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        result.put("cursorEnd", cursorEnd);
        result.put("hasMore", hasMore);
        log.debug("Exiting fetchChangesSince cursorEnd={} hasMore={}", cursorEnd, hasMore);
        return result;
    }
}

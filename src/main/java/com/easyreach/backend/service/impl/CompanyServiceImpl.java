package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;
import com.easyreach.backend.entity.Company;
import com.easyreach.backend.mapper.CompanyMapper;
import com.easyreach.backend.repository.CompanyRepository;
import com.easyreach.backend.service.CompanyService;
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
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    @Override
    public ApiResponse<CompanyResponseDto> create(CompanyRequestDto dto) {
        Company entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<CompanyResponseDto> update(String id, CompanyRequestDto dto) {
        Company e = repository.findByUuidAndDeletedIsFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        Company e = repository.findByUuidAndDeletedIsFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found: " + id));
        e.setDeleted(true);
        e.setDeletedAt(OffsetDateTime.now());
        repository.save(e);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<CompanyResponseDto> get(String id) {
        Company e = repository.findByUuidAndDeletedIsFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<CompanyResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findByDeletedIsFalse(pageable).map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<CompanyRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        Map<String, CompanyRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getUuid() != null)
                .collect(Collectors.toMap(CompanyRequestDto::getUuid, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) return 0;

        Map<String, Company> existing = repository.findAllById(dtoMap.keySet()).stream()
                .collect(Collectors.toMap(Company::getUuid, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<Company> entities = new ArrayList<>();
        for (CompanyRequestDto dto : dtoMap.values()) {
            Company entity = existing.get(dto.getUuid());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                Company e = mapper.toEntity(dto);
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

        List<Company> updates = repository.findByUuidAndUpdatedAtGreaterThanEqual(
                companyUuid, cursor, PageRequest.of(0, limit + 1));
        if (updates.size() > limit) {
            hasMore = true;
            updates = updates.subList(0, limit);
        }
        result.put("items", updates.stream().map(mapper::toDto).toList());

        int remaining = limit - updates.size();
        List<Company> tombstoneEntities = Collections.emptyList();
        if (remaining > 0) {
            tombstoneEntities = repository.findByUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(
                    companyUuid, cursor, PageRequest.of(0, remaining + 1));
            if (tombstoneEntities.size() > remaining) {
                hasMore = true;
                tombstoneEntities = tombstoneEntities.subList(0, remaining);
            }
        }
        result.put("tombstones", tombstoneEntities.stream().map(Company::getUuid).toList());

        OffsetDateTime cursorEnd = cursor;
        for (Company e : updates) {
            OffsetDateTime ts = e.getUpdatedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        for (Company e : tombstoneEntities) {
            OffsetDateTime ts = e.getDeletedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        result.put("cursorEnd", cursorEnd);
        result.put("hasMore", hasMore);
        return result;
    }
}

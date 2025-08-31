package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.entity.VehicleEntry;
import com.easyreach.backend.mapper.VehicleEntryMapper;
import com.easyreach.backend.repository.VehicleEntryRepository;
import com.easyreach.backend.service.VehicleEntryService;
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
public class VehicleEntryServiceImpl extends CompanyScopedService implements VehicleEntryService {
    private final VehicleEntryRepository repository;
    private final VehicleEntryMapper mapper;

    @Override
    public ApiResponse<VehicleEntryResponseDto> create(VehicleEntryRequestDto dto) {
        VehicleEntry entity = mapper.toEntity(dto);
        entity.setCompanyUuid(currentCompany());
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<VehicleEntryResponseDto> update(String id, VehicleEntryRequestDto dto) {
        VehicleEntry e = repository.findByEntryIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> new EntityNotFoundException("VehicleEntry not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        VehicleEntry e = repository.findByEntryIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> new EntityNotFoundException("VehicleEntry not found: " + id));
        e.setDeleted(true);
        e.setDeletedAt(OffsetDateTime.now());
        repository.save(e);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<VehicleEntryResponseDto> get(String id) {
        VehicleEntry e = repository.findByEntryIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> new EntityNotFoundException("VehicleEntry not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<VehicleEntryResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findByCompanyUuidAndDeletedIsFalse(currentCompany(), pageable).map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<VehicleEntryRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        Map<String, VehicleEntryRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getEntryId() != null)
                .collect(Collectors.toMap(VehicleEntryRequestDto::getEntryId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) return 0;

        Map<String, VehicleEntry> existing = repository.findAllById(dtoMap.keySet()).stream()
                .collect(Collectors.toMap(VehicleEntry::getEntryId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<VehicleEntry> entities = new ArrayList<>();
        for (VehicleEntryRequestDto dto : dtoMap.values()) {
            VehicleEntry entity = existing.get(dto.getEntryId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                VehicleEntry e = mapper.toEntity(dto);
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

        List<VehicleEntry> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(
                companyUuid, cursor, PageRequest.of(0, limit + 1));
        if (updates.size() > limit) {
            hasMore = true;
            updates = updates.subList(0, limit);
        }
        result.put("items", updates.stream().map(mapper::toDto).toList());

        int remaining = limit - updates.size();
        List<VehicleEntry> tombstoneEntities = Collections.emptyList();
        if (remaining > 0) {
            tombstoneEntities = repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(
                    companyUuid, cursor, PageRequest.of(0, remaining + 1));
            if (tombstoneEntities.size() > remaining) {
                hasMore = true;
                tombstoneEntities = tombstoneEntities.subList(0, remaining);
            }
        }
        result.put("tombstones", tombstoneEntities.stream().map(VehicleEntry::getEntryId).toList());

        OffsetDateTime cursorEnd = cursor;
        for (VehicleEntry e : updates) {
            OffsetDateTime ts = e.getUpdatedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        for (VehicleEntry e : tombstoneEntities) {
            OffsetDateTime ts = e.getDeletedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        result.put("cursorEnd", cursorEnd);
        result.put("hasMore", hasMore);
        return result;
    }
}

package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleRequestDto;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleResponseDto;
import com.easyreach.backend.entity.InternalVehicle;
import com.easyreach.backend.mapper.InternalVehicleMapper;
import com.easyreach.backend.repository.InternalVehicleRepository;
import com.easyreach.backend.service.InternalVehicleService;
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
public class InternalVehicleServiceImpl extends CompanyScopedService implements InternalVehicleService {
    private final InternalVehicleRepository repository;
    private final InternalVehicleMapper mapper;

    @Override
    public ApiResponse<InternalVehicleResponseDto> create(InternalVehicleRequestDto dto) {
        InternalVehicle entity = mapper.toEntity(dto);
        entity.setCompanyUuid(currentCompany());
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<InternalVehicleResponseDto> update(String id, InternalVehicleRequestDto dto) {
        InternalVehicle e = repository.findByVehicleIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> new EntityNotFoundException("InternalVehicle not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        InternalVehicle e = repository.findByVehicleIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> new EntityNotFoundException("InternalVehicle not found: " + id));
        e.setDeleted(true);
        e.setDeletedAt(OffsetDateTime.now());
        repository.save(e);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<InternalVehicleResponseDto> get(String id) {
        InternalVehicle e = repository.findByVehicleIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> new EntityNotFoundException("InternalVehicle not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<InternalVehicleResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findByCompanyUuidAndDeletedIsFalse(currentCompany(), pageable).map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<InternalVehicleRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        Map<String, InternalVehicleRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getVehicleId() != null)
                .collect(Collectors.toMap(InternalVehicleRequestDto::getVehicleId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) return 0;

        Map<String, InternalVehicle> existing = repository.findAllById(dtoMap.keySet()).stream()
                .collect(Collectors.toMap(InternalVehicle::getVehicleId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<InternalVehicle> entities = new ArrayList<>();
        for (InternalVehicleRequestDto dto : dtoMap.values()) {
            InternalVehicle entity = existing.get(dto.getVehicleId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                InternalVehicle e = mapper.toEntity(dto);
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

        List<InternalVehicle> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(
                companyUuid, cursor, PageRequest.of(0, limit + 1));
        if (updates.size() > limit) {
            hasMore = true;
            updates = updates.subList(0, limit);
        }
        result.put("items", updates.stream().map(mapper::toDto).toList());

        int remaining = limit - updates.size();
        List<InternalVehicle> tombstoneEntities = Collections.emptyList();
        if (remaining > 0) {
            tombstoneEntities = repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(
                    companyUuid, cursor, PageRequest.of(0, remaining + 1));
            if (tombstoneEntities.size() > remaining) {
                hasMore = true;
                tombstoneEntities = tombstoneEntities.subList(0, remaining);
            }
        }
        result.put("tombstones", tombstoneEntities.stream().map(InternalVehicle::getVehicleId).toList());

        OffsetDateTime cursorEnd = cursor;
        for (InternalVehicle e : updates) {
            OffsetDateTime ts = e.getUpdatedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        for (InternalVehicle e : tombstoneEntities) {
            OffsetDateTime ts = e.getDeletedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        result.put("cursorEnd", cursorEnd);
        result.put("hasMore", hasMore);
        return result;
    }
}

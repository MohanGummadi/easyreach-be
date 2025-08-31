package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeRequestDto;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeResponseDto;
import com.easyreach.backend.entity.VehicleType;
import com.easyreach.backend.mapper.VehicleTypeMapper;
import com.easyreach.backend.repository.VehicleTypeRepository;
import com.easyreach.backend.security.CompanyContext;
import com.easyreach.backend.service.VehicleTypeService;
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
public class VehicleTypeServiceImpl implements VehicleTypeService {
    private final VehicleTypeRepository repository;
    private final VehicleTypeMapper mapper;

    @Override
    public ApiResponse<VehicleTypeResponseDto> create(VehicleTypeRequestDto dto) {
        VehicleType entity = mapper.toEntity(dto);
        entity.setCompanyUuid(CompanyContext.getRequiredCompanyId());
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<VehicleTypeResponseDto> update(String id, VehicleTypeRequestDto dto) {
        VehicleType e = repository.findByIdAndCompanyUuidAndDeletedIsFalse(id, CompanyContext.getRequiredCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("VehicleType not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        VehicleType e = repository.findByIdAndCompanyUuidAndDeletedIsFalse(id, CompanyContext.getRequiredCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("VehicleType not found: " + id));
        e.setDeleted(true);
        e.setDeletedAt(OffsetDateTime.now());
        repository.save(e);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<VehicleTypeResponseDto> get(String id) {
        VehicleType e = repository.findByIdAndCompanyUuidAndDeletedIsFalse(id, CompanyContext.getRequiredCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("VehicleType not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<VehicleTypeResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository
                .findByCompanyUuidAndDeletedIsFalse(CompanyContext.getRequiredCompanyId(), pageable)
                .map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<VehicleTypeRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        Map<String, VehicleTypeRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getId() != null)
                .collect(Collectors.toMap(VehicleTypeRequestDto::getId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) return 0;

        String companyUuid = CompanyContext.getRequiredCompanyId();
        Map<String, VehicleType> existing = repository
                .findByIdInAndCompanyUuid(dtoMap.keySet(), companyUuid).stream()
                .collect(Collectors.toMap(VehicleType::getId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<VehicleType> entities = new ArrayList<>();
        for (VehicleTypeRequestDto dto : dtoMap.values()) {
            VehicleType entity = existing.get(dto.getId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                VehicleType e = mapper.toEntity(dto);
                e.setCreatedAt(now);
                e.setUpdatedAt(now);
                e.setIsSynced(true);
                e.setCompanyUuid(companyUuid);
                entities.add(e);
            }
        }
        repository.saveAll(entities);
        return entities.size();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit) {
        String currentCompany = CompanyContext.getRequiredCompanyId();
        if (!currentCompany.equals(companyUuid)) {
            throw new IllegalArgumentException("Company mismatch");
        }

        Map<String, Object> result = new HashMap<>();
        boolean hasMore = false;

        List<VehicleType> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(
                currentCompany, cursor, PageRequest.of(0, limit + 1));
        if (updates.size() > limit) {
            hasMore = true;
            updates = updates.subList(0, limit);
        }
        result.put("items", updates.stream().map(mapper::toDto).toList());

        int remaining = limit - updates.size();
        List<VehicleType> tombstoneEntities = Collections.emptyList();
        if (remaining > 0) {
            tombstoneEntities = repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(
                    currentCompany, cursor, PageRequest.of(0, remaining + 1));
            if (tombstoneEntities.size() > remaining) {
                hasMore = true;
                tombstoneEntities = tombstoneEntities.subList(0, remaining);
            }
        }
        result.put("tombstones", tombstoneEntities.stream().map(VehicleType::getId).toList());

        OffsetDateTime cursorEnd = cursor;
        for (VehicleType e : updates) {
            OffsetDateTime ts = e.getUpdatedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        for (VehicleType e : tombstoneEntities) {
            OffsetDateTime ts = e.getDeletedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        result.put("cursorEnd", cursorEnd);
        result.put("hasMore", hasMore);
        return result;
    }
}

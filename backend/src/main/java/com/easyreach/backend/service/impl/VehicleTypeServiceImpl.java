package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeRequestDto;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeResponseDto;
import com.easyreach.backend.entity.VehicleType;
import com.easyreach.backend.mapper.VehicleTypeMapper;
import com.easyreach.backend.repository.VehicleTypeRepository;
import com.easyreach.backend.service.VehicleTypeService;
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
public class VehicleTypeServiceImpl extends CompanyScopedService implements VehicleTypeService {
    private final VehicleTypeRepository repository;
    private final VehicleTypeMapper mapper;

    @Override
    public ApiResponse<VehicleTypeResponseDto> create(VehicleTypeRequestDto dto) {
        log.debug("Entering create with dto={}", dto);
        VehicleType entity = mapper.toEntity(dto);
        entity.setCompanyUuid(currentCompany());
        ApiResponse<VehicleTypeResponseDto> response = ApiResponse.success(mapper.toDto(repository.save(entity)));
        log.debug("Exiting create with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<VehicleTypeResponseDto> update(String id, VehicleTypeRequestDto dto) {
        log.debug("Entering update with id={} dto={}", id, dto);
        VehicleType e = repository.findByIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("VehicleType not found: {}", id);
                    return new EntityNotFoundException("VehicleType not found: " + id);
                });
        mapper.update(e, dto);
        ApiResponse<VehicleTypeResponseDto> response = ApiResponse.success(mapper.toDto(repository.save(e)));
        log.debug("Exiting update with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        log.debug("Entering delete with id={}", id);
        VehicleType e = repository.findByIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("VehicleType not found: {}", id);
                    return new EntityNotFoundException("VehicleType not found: " + id);
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
    public ApiResponse<VehicleTypeResponseDto> get(String id) {
        log.debug("Entering get with id={}", id);
        VehicleType e = repository.findByIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("VehicleType not found: {}", id);
                    return new EntityNotFoundException("VehicleType not found: " + id);
                });
        ApiResponse<VehicleTypeResponseDto> response = ApiResponse.success(mapper.toDto(e));
        log.debug("Exiting get with response={}", response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<VehicleTypeResponseDto>> list(Pageable pageable) {
        log.debug("Entering list with pageable={}", pageable);
        ApiResponse<Page<VehicleTypeResponseDto>> response = ApiResponse.success(repository
                .findByCompanyUuidAndDeletedIsFalse(currentCompany(), pageable)
                .map(mapper::toDto));
        log.debug("Exiting list with response={}", response);
        return response;
    }

    @Override
    public int bulkSync(List<VehicleTypeRequestDto> dtos) {
        log.debug("Entering bulkSync with {} dtos", dtos != null ? dtos.size() : 0);
        if (dtos == null || dtos.isEmpty()) {
            log.warn("bulkSync called with empty dto list");
            return 0;
        }
        Map<String, VehicleTypeRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getId() != null)
                .collect(Collectors.toMap(VehicleTypeRequestDto::getId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) {
            log.warn("bulkSync dtoMap empty after filtering ids");
            return 0;
        }

        Map<String, VehicleType> existing = repository.findByIdInAndCompanyUuid(dtoMap.keySet(), currentCompany()).stream()
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
                e.setCompanyUuid(currentCompany());
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

        List<VehicleType> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(
                companyUuid, cursor, PageRequest.of(0, limit + 1));
        if (updates.size() > limit) {
            hasMore = true;
            updates = updates.subList(0, limit);
        }
        result.put("items", updates.stream().map(mapper::toDto).toList());

        int remaining = limit - updates.size();
        List<VehicleType> tombstoneEntities = Collections.emptyList();
        if (remaining > 0) {
            tombstoneEntities = repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(
                    companyUuid, cursor, PageRequest.of(0, remaining + 1));
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
        log.debug("Exiting fetchChangesSince with hasMore={} cursorEnd={}", hasMore, cursorEnd);
        return result;
    }
}

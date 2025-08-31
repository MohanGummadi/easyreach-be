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
public class DieselUsageServiceImpl implements DieselUsageService {
    private final DieselUsageRepository repository;
    private final DieselUsageMapper mapper;

    @Override
    public ApiResponse<DieselUsageResponseDto> create(DieselUsageRequestDto dto) {
        DieselUsage entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<DieselUsageResponseDto> update(String id, DieselUsageRequestDto dto) {
        DieselUsage e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("DieselUsage not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("DieselUsage not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<DieselUsageResponseDto> get(String id) {
        DieselUsage e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("DieselUsage not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<DieselUsageResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<DieselUsageRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        Map<String, DieselUsageRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getDieselUsageId() != null)
                .collect(Collectors.toMap(DieselUsageRequestDto::getDieselUsageId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) return 0;

        Map<String, DieselUsage> existing = repository.findAllById(dtoMap.keySet()).stream()
                .collect(Collectors.toMap(DieselUsage::getDieselUsageId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<DieselUsage> entities = new ArrayList<>();
        for (DieselUsageRequestDto dto : dtoMap.values()) {
            DieselUsage entity = existing.get(dto.getDieselUsageId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setChangeId(entity.getChangeId() == null ? 0L : entity.getChangeId() + 1);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                DieselUsage e = mapper.toEntity(dto);
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
        List<DieselUsage> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(companyUuid, cursor, PageRequest.of(0, limit));
        result.put("updated", updates.stream().map(mapper::toDto).toList());
        int remaining = limit - updates.size();
        List<String> tombstones = remaining > 0
                ? repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(companyUuid, cursor, PageRequest.of(0, remaining))
                .stream().map(DieselUsage::getDieselUsageId).toList()
                : Collections.emptyList();
        result.put("tombstones", tombstones);
        return result;
    }
}

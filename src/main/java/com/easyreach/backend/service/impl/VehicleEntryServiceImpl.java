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
public class VehicleEntryServiceImpl implements VehicleEntryService {
    private final VehicleEntryRepository repository;
    private final VehicleEntryMapper mapper;

    @Override
    public ApiResponse<VehicleEntryResponseDto> create(VehicleEntryRequestDto dto) {
        VehicleEntry entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<VehicleEntryResponseDto> update(String id, VehicleEntryRequestDto dto) {
        VehicleEntry e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("VehicleEntry not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("VehicleEntry not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<VehicleEntryResponseDto> get(String id) {
        VehicleEntry e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("VehicleEntry not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<VehicleEntryResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
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
                entity.setChangeId(entity.getChangeId() == null ? 0L : entity.getChangeId() + 1);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                VehicleEntry e = mapper.toEntity(dto);
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
        List<VehicleEntry> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(companyUuid, cursor, PageRequest.of(0, limit));
        result.put("updated", updates.stream().map(mapper::toDto).toList());
        int remaining = limit - updates.size();
        List<String> tombstones = remaining > 0
                ? repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(companyUuid, cursor, PageRequest.of(0, remaining))
                .stream().map(VehicleEntry::getEntryId).toList()
                : Collections.emptyList();
        result.put("tombstones", tombstones);
        return result;
    }
}

package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageRequestDto;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageResponseDto;
import com.easyreach.backend.entity.EquipmentUsage;
import com.easyreach.backend.mapper.EquipmentUsageMapper;
import com.easyreach.backend.repository.EquipmentUsageRepository;
import com.easyreach.backend.service.EquipmentUsageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipmentUsageServiceImpl implements EquipmentUsageService {
    private final EquipmentUsageRepository repository;
    private final EquipmentUsageMapper mapper;

    @Override
    public ApiResponse<EquipmentUsageResponseDto> create(EquipmentUsageRequestDto dto) {
        EquipmentUsage entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<EquipmentUsageResponseDto> update(String id, EquipmentUsageRequestDto dto) {
        EquipmentUsage e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("EquipmentUsage not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("EquipmentUsage not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<EquipmentUsageResponseDto> get(String id) {
        EquipmentUsage e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("EquipmentUsage not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<EquipmentUsageResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<EquipmentUsageRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        List<EquipmentUsage> entities = dtos.stream()
                .map(mapper::toEntity)
                .peek(e -> e.setIsSynced(true))
                .toList();
        repository.saveAll(entities);
        return entities.size();
    }
}

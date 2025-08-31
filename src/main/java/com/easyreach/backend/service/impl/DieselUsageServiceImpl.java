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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List<DieselUsage> entities = dtos.stream()
                .map(mapper::toEntity)
                .peek(e -> e.setIsSynced(true))
                .toList();
        repository.saveAll(entities);
        return entities.size();
    }
}

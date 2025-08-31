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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List<VehicleEntry> entities = dtos.stream()
                .map(mapper::toEntity)
                .peek(e -> e.setIsSynced(true))
                .toList();
        repository.saveAll(entities);
        return entities.size();
    }
}

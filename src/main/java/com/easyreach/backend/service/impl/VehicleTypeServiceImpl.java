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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleTypeServiceImpl implements VehicleTypeService {
    private final VehicleTypeRepository repository;
    private final VehicleTypeMapper mapper;

    @Override
    public ApiResponse<VehicleTypeResponseDto> create(VehicleTypeRequestDto dto) {
        VehicleType entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<VehicleTypeResponseDto> update(String id, VehicleTypeRequestDto dto) {
        VehicleType e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("VehicleType not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("VehicleType not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<VehicleTypeResponseDto> get(String id) {
        VehicleType e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("VehicleType not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<VehicleTypeResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<VehicleTypeRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        List<VehicleType> entities = dtos.stream()
                .map(mapper::toEntity)
                .peek(e -> e.setIsSynced(true))
                .toList();
        repository.saveAll(entities);
        return entities.size();
    }
}

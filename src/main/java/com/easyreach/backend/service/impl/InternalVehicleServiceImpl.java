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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InternalVehicleServiceImpl implements InternalVehicleService {
    private final InternalVehicleRepository repository;
    private final InternalVehicleMapper mapper;

    @Override
    public ApiResponse<InternalVehicleResponseDto> create(InternalVehicleRequestDto dto) {
        InternalVehicle entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<InternalVehicleResponseDto> update(String id, InternalVehicleRequestDto dto) {
        InternalVehicle e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("InternalVehicle not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("InternalVehicle not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<InternalVehicleResponseDto> get(String id) {
        InternalVehicle e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("InternalVehicle not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<InternalVehicleResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
    }
}

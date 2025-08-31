package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.expense_master.ExpenseMasterRequestDto;
import com.easyreach.backend.dto.expense_master.ExpenseMasterResponseDto;
import com.easyreach.backend.entity.ExpenseMaster;
import com.easyreach.backend.mapper.ExpenseMasterMapper;
import com.easyreach.backend.repository.ExpenseMasterRepository;
import com.easyreach.backend.service.ExpenseMasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class ExpenseMasterServiceImpl implements ExpenseMasterService {
    private final ExpenseMasterRepository repository;
    private final ExpenseMasterMapper mapper;

    @Override
    public ApiResponse<ExpenseMasterResponseDto> create(ExpenseMasterRequestDto dto) {
        ExpenseMaster entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<ExpenseMasterResponseDto> update(String id, ExpenseMasterRequestDto dto) {
        ExpenseMaster e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ExpenseMaster not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("ExpenseMaster not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<ExpenseMasterResponseDto> get(String id) {
        ExpenseMaster e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ExpenseMaster not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<ExpenseMasterResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<ExpenseMasterRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        Map<String, ExpenseMasterRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getId() != null)
                .collect(Collectors.toMap(ExpenseMasterRequestDto::getId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) return 0;

        Map<String, ExpenseMaster> existing = repository.findAllById(dtoMap.keySet()).stream()
                .collect(Collectors.toMap(ExpenseMaster::getId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<ExpenseMaster> entities = new ArrayList<>();
        for (ExpenseMasterRequestDto dto : dtoMap.values()) {
            ExpenseMaster entity = existing.get(dto.getId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                ExpenseMaster e = mapper.toEntity(dto);
                e.setCreatedAt(now);
                e.setUpdatedAt(now);
                e.setIsSynced(true);
                entities.add(e);
            }
        }
        repository.saveAll(entities);
        return entities.size();
    }
}

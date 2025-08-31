package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseRequestDto;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseResponseDto;
import com.easyreach.backend.entity.DailyExpense;
import com.easyreach.backend.mapper.DailyExpenseMapper;
import com.easyreach.backend.repository.DailyExpenseRepository;
import com.easyreach.backend.service.DailyExpenseService;
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
public class DailyExpenseServiceImpl implements DailyExpenseService {
    private final DailyExpenseRepository repository;
    private final DailyExpenseMapper mapper;

    @Override
    public ApiResponse<DailyExpenseResponseDto> create(DailyExpenseRequestDto dto) {
        DailyExpense entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<DailyExpenseResponseDto> update(String id, DailyExpenseRequestDto dto) {
        DailyExpense e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("DailyExpense not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("DailyExpense not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<DailyExpenseResponseDto> get(String id) {
        DailyExpense e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("DailyExpense not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<DailyExpenseResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<DailyExpenseRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        List<DailyExpense> entities = dtos.stream()
                .map(mapper::toEntity)
                .peek(e -> e.setIsSynced(true))
                .toList();
        repository.saveAll(entities);
        return entities.size();
    }
}

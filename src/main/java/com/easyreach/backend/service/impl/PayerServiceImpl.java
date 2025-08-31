package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.entity.Payer;
import com.easyreach.backend.mapper.PayerMapper;
import com.easyreach.backend.repository.PayerRepository;
import com.easyreach.backend.service.PayerService;
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
public class PayerServiceImpl implements PayerService {
    private final PayerRepository repository;
    private final PayerMapper mapper;

    @Override
    public ApiResponse<PayerResponseDto> create(PayerRequestDto dto) {
        Payer entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<PayerResponseDto> update(String id, PayerRequestDto dto) {
        Payer e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payer not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Payer not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<PayerResponseDto> get(String id) {
        Payer e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payer not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<PayerResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
    }

    @Override
    public int bulkSync(List<PayerRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return 0;
        Map<String, PayerRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getPayerId() != null)
                .collect(Collectors.toMap(PayerRequestDto::getPayerId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) return 0;

        Map<String, Payer> existing = repository.findAllById(dtoMap.keySet()).stream()
                .collect(Collectors.toMap(Payer::getPayerId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<Payer> entities = new ArrayList<>();
        for (PayerRequestDto dto : dtoMap.values()) {
            Payer entity = existing.get(dto.getPayerId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setChangeId(entity.getChangeId() == null ? 0L : entity.getChangeId() + 1);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                Payer e = mapper.toEntity(dto);
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
        List<Payer> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(companyUuid, cursor, PageRequest.of(0, limit));
        result.put("updated", updates.stream().map(mapper::toDto).toList());
        int remaining = limit - updates.size();
        List<String> tombstones = remaining > 0
                ? repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(companyUuid, cursor, PageRequest.of(0, remaining))
                .stream().map(Payer::getPayerId).toList()
                : Collections.emptyList();
        result.put("tombstones", tombstones);
        return result;
    }
}

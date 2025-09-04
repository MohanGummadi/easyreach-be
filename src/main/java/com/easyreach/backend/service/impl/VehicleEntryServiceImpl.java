package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.entity.VehicleEntry;
import com.easyreach.backend.entity.PayerSettlement;
import com.easyreach.backend.mapper.VehicleEntryMapper;
import com.easyreach.backend.repository.VehicleEntryRepository;
import com.easyreach.backend.repository.PayerSettlementRepository;
import com.easyreach.backend.util.CodeGenerator;
import com.easyreach.backend.service.VehicleEntryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class VehicleEntryServiceImpl extends CompanyScopedService implements VehicleEntryService {
    private final VehicleEntryRepository repository;
    private final VehicleEntryMapper mapper;
    private final PayerSettlementRepository payerSettlementRepository;
    private final CodeGenerator codeGenerator;

    @Override
    public ApiResponse<VehicleEntryResponseDto> create(VehicleEntryRequestDto dto) {
        log.debug("Entering create with dto={}", dto);

        VehicleEntry entity = mapper.toEntity(dto);
        entity.setCompanyUuid(currentCompany());
        // generate entry code
        entity.setEntryId(codeGenerator.generate("VE"));

        // initialise payment related fields depending on pay type
        if (dto.getPaytype() != null &&
                ("CASH".equalsIgnoreCase(dto.getPaytype()) || "UPI".equalsIgnoreCase(dto.getPaytype()))) {
            entity.setPaidAmount(dto.getAmount());
            entity.setPendingAmt(java.math.BigDecimal.ZERO);
            entity.setIsSettled(true);
            entity.setSettlementType(dto.getPaytype());
            entity.setSettlementDate(OffsetDateTime.now());

            // create payer settlement record for immediate payments
            PayerSettlement ps = new PayerSettlement();
            OffsetDateTime now = OffsetDateTime.now();
            ps.setSettlementId(codeGenerator.generate("PS"));
            ps.setPayerId(dto.getPayerId());
            ps.setAmount(dto.getAmount());
            ps.setDate(now);
            ps.setCompanyUuid(currentCompany());
            ps.setIsSynced(true);
            ps.setCreatedAt(now);
            ps.setUpdatedAt(now);
            ps.setDeleted(false);
            payerSettlementRepository.save(ps);
        } else {
            entity.setPaidAmount(java.math.BigDecimal.ZERO);
            entity.setPendingAmt(dto.getAmount());
            entity.setIsSettled(false);
        }

        ApiResponse<VehicleEntryResponseDto> response =
                ApiResponse.success(mapper.toDto(repository.save(entity)));
        log.debug("Exiting create with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<VehicleEntryResponseDto> update(String id, VehicleEntryRequestDto dto) {
        log.debug("Entering update with id={} dto={}", id, dto);
        VehicleEntry e = repository.findByEntryIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("VehicleEntry not found: {}", id);
                    return new EntityNotFoundException("VehicleEntry not found: " + id);
                });
        mapper.update(e, dto);
        ApiResponse<VehicleEntryResponseDto> response = ApiResponse.success(mapper.toDto(repository.save(e)));
        log.debug("Exiting update with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        log.debug("Entering delete with id={}", id);
        VehicleEntry e = repository.findByEntryIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("VehicleEntry not found: {}", id);
                    return new EntityNotFoundException("VehicleEntry not found: " + id);
                });
        e.setDeleted(true);
        e.setDeletedAt(OffsetDateTime.now());
        repository.save(e);
        ApiResponse<Void> response = ApiResponse.success(null);
        log.debug("Exiting delete with response={}", response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<VehicleEntryResponseDto> get(String id) {
        log.debug("Entering get with id={}", id);
        VehicleEntry e = repository.findByEntryIdAndCompanyUuidAndDeletedIsFalse(id, currentCompany())
                .orElseThrow(() -> {
                    log.error("VehicleEntry not found: {}", id);
                    return new EntityNotFoundException("VehicleEntry not found: " + id);
                });
        ApiResponse<VehicleEntryResponseDto> response = ApiResponse.success(mapper.toDto(e));
        log.debug("Exiting get with response={}", response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<VehicleEntryResponseDto>> list(Pageable pageable) {
        log.debug("Entering list with pageable={}", pageable);
        ApiResponse<Page<VehicleEntryResponseDto>> response = ApiResponse.success(
                repository.findByCompanyUuidAndDeletedIsFalse(currentCompany(), pageable).map(mapper::toDto));
        log.debug("Exiting list with response={}", response);
        return response;
    }

    @Override
    public int bulkSync(List<VehicleEntryRequestDto> dtos) {
        log.debug("Entering bulkSync with {} dtos", dtos != null ? dtos.size() : 0);
        if (dtos == null || dtos.isEmpty()) {
            log.warn("bulkSync called with empty dto list");
            return 0;
        }
        Map<String, VehicleEntryRequestDto> dtoMap = dtos.stream()
                .filter(d -> d.getEntryId() != null)
                .collect(Collectors.toMap(VehicleEntryRequestDto::getEntryId, Function.identity(), (a, b) -> b, LinkedHashMap::new));
        if (dtoMap.isEmpty()) {
            log.warn("bulkSync dtoMap empty after filtering ids");
            return 0;
        }

        Map<String, VehicleEntry> existing = repository.findByEntryIdInAndCompanyUuid(dtoMap.keySet(), currentCompany()).stream()
                .collect(Collectors.toMap(VehicleEntry::getEntryId, Function.identity()));

        OffsetDateTime now = OffsetDateTime.now();
        List<VehicleEntry> entities = new ArrayList<>();
        for (VehicleEntryRequestDto dto : dtoMap.values()) {
            VehicleEntry entity = existing.get(dto.getEntryId());
            if (entity != null) {
                mapper.merge(dto, entity);
                entity.setUpdatedAt(now);
                entity.setIsSynced(true);
                entities.add(entity);
            } else {
                VehicleEntry e = mapper.toEntity(dto);
                e.setCompanyUuid(currentCompany());
                e.setCreatedAt(now);
                e.setUpdatedAt(now);
                e.setIsSynced(true);
                entities.add(e);
            }
        }
        repository.saveAll(entities);
        int size = entities.size();
        log.debug("Exiting bulkSync with size={}", size);
        return size;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> fetchChangesSince(String companyUuid, OffsetDateTime cursor, int limit) {
        log.debug("Entering fetchChangesSince companyUuid={} cursor={} limit={}", companyUuid, cursor, limit);
        Map<String, Object> result = new HashMap<>();
        boolean hasMore = false;

        List<VehicleEntry> updates = repository.findByCompanyUuidAndUpdatedAtGreaterThanEqual(
                companyUuid, cursor, PageRequest.of(0, limit + 1));
        if (updates.size() > limit) {
            hasMore = true;
            updates = updates.subList(0, limit);
        }
        result.put("items", updates.stream().map(mapper::toDto).toList());

        int remaining = limit - updates.size();
        List<VehicleEntry> tombstoneEntities = Collections.emptyList();
        if (remaining > 0) {
            tombstoneEntities = repository.findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(
                    companyUuid, cursor, PageRequest.of(0, remaining + 1));
            if (tombstoneEntities.size() > remaining) {
                hasMore = true;
                tombstoneEntities = tombstoneEntities.subList(0, remaining);
            }
        }
        result.put("tombstones", tombstoneEntities.stream().map(VehicleEntry::getEntryId).toList());

        OffsetDateTime cursorEnd = cursor;
        for (VehicleEntry e : updates) {
            OffsetDateTime ts = e.getUpdatedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        for (VehicleEntry e : tombstoneEntities) {
            OffsetDateTime ts = e.getDeletedAt();
            if (ts != null && ts.isAfter(cursorEnd)) cursorEnd = ts;
        }
        result.put("cursorEnd", cursorEnd);
        result.put("hasMore", hasMore);
        log.debug("Exiting fetchChangesSince cursorEnd={} hasMore={}", cursorEnd, hasMore);
        return result;
    }
}

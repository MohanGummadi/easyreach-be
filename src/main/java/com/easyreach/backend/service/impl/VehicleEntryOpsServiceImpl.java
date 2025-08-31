package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.entity.VehicleEntry;
import com.easyreach.backend.mapper.VehicleEntryMapper;
import com.easyreach.backend.repository.VehicleEntryRepository;
import com.easyreach.backend.service.VehicleEntryOpsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VehicleEntryOpsServiceImpl extends CompanyScopedService implements VehicleEntryOpsService {

    private final VehicleEntryRepository repository;
    private final VehicleEntryMapper mapper;

    @Override
    public ApiResponse<VehicleEntryResponseDto> addPayment(String entryId, BigDecimal amount, String receivedBy, OffsetDateTime when) {
        log.debug("Entering addPayment entryId={} amount={} receivedBy={} when={}", entryId, amount, receivedBy, when);
        VehicleEntry e = repository.findByEntryIdAndCompanyUuidAndDeletedIsFalse(entryId, currentCompany())
                .orElseThrow(() -> {
                    log.error("VehicleEntry not found: {}", entryId);
                    return new EntityNotFoundException("VehicleEntry not found: " + entryId);
                });
        if (amount == null || amount.signum() <= 0) {
            log.error("Invalid amount {} for entry {}", amount, entryId);
            throw new IllegalArgumentException("Amount must be positive");
        }
        e.setPaidAmount(e.getPaidAmount().add(amount));
        e.setPaymentReceivedBy(receivedBy);
        if (e.getAmount() != null && e.getPaidAmount() != null) {
            BigDecimal pending = e.getAmount().subtract(e.getPaidAmount());
            if (pending.signum() < 0) pending = BigDecimal.ZERO;
            e.setPendingAmt(pending);
            e.setIsSettled(pending.compareTo(BigDecimal.ZERO) == 0);
            if (Boolean.TRUE.equals(e.getIsSettled())) {
                e.setSettlementDate(when != null ? when : OffsetDateTime.now());
                e.setSettlementType(e.getPaytype());
            }
        }
        repository.save(e);
        ApiResponse<VehicleEntryResponseDto> response = ApiResponse.success(mapper.toDto(e));
        log.debug("Exiting addPayment with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<VehicleEntryResponseDto> markExit(String entryId, OffsetDateTime when) {
        log.debug("Entering markExit entryId={} when={}", entryId, when);
        VehicleEntry e = repository.findByEntryIdAndCompanyUuidAndDeletedIsFalse(entryId, currentCompany())
                .orElseThrow(() -> {
                    log.error("VehicleEntry not found: {}", entryId);
                    return new EntityNotFoundException("VehicleEntry not found: " + entryId);
                });
        e.setExitTime(when != null ? when : OffsetDateTime.now());
        repository.save(e);
        ApiResponse<VehicleEntryResponseDto> response = ApiResponse.success(mapper.toDto(e));
        log.debug("Exiting markExit with response={}", response);
        return response;
    }
}

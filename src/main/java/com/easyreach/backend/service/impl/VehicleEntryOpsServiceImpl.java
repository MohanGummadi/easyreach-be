package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.entity.VehicleEntry;
import com.easyreach.backend.mapper.VehicleEntryMapper;
import com.easyreach.backend.repository.VehicleEntryRepository;
import com.easyreach.backend.service.VehicleEntryOpsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleEntryOpsServiceImpl extends CompanyScopedService implements VehicleEntryOpsService {

    private final VehicleEntryRepository repository;
    private final VehicleEntryMapper mapper;

    @Override
    public ApiResponse<VehicleEntryResponseDto> addPayment(String entryId, BigDecimal amount, String receivedBy, OffsetDateTime when) {
        VehicleEntry e = repository.findByEntryIdAndCompanyUuidAndDeletedIsFalse(entryId, currentCompany()).orElseThrow(() -> new EntityNotFoundException("VehicleEntry not found: " + entryId));
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Amount must be positive");
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
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    public ApiResponse<VehicleEntryResponseDto> markExit(String entryId, OffsetDateTime when) {
        VehicleEntry e = repository.findByEntryIdAndCompanyUuidAndDeletedIsFalse(entryId, currentCompany()).orElseThrow(() -> new EntityNotFoundException("VehicleEntry not found: " + entryId));
        e.setExitTime(when != null ? when : OffsetDateTime.now());
        repository.save(e);
        return ApiResponse.success(mapper.toDto(e));
    }
}

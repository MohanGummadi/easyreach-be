package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.ledger.ApplyPaymentRequest;
import com.easyreach.backend.dto.ledger.PayerLedgerSummaryDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.entity.PayerSettlement;
import com.easyreach.backend.entity.VehicleEntry;
import com.easyreach.backend.mapper.VehicleEntryMapper;
import com.easyreach.backend.repository.PayerLedgerSummary;
import com.easyreach.backend.repository.PayerSettlementRepository;
import com.easyreach.backend.repository.VehicleEntryRepository;
import com.easyreach.backend.service.PayerLedgerService;
import com.easyreach.backend.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PayerLedgerServiceImpl extends CompanyScopedService implements PayerLedgerService {

    private final VehicleEntryRepository vehicleEntryRepository;
    private final VehicleEntryMapper vehicleEntryMapper;
    private final PayerSettlementRepository payerSettlementRepository;
    private final CodeGenerator codeGenerator;

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<VehicleEntryResponseDto>> getLedgerForPayer(String payerId, Pageable pageable) {
        Page<VehicleEntry> page = vehicleEntryRepository
                .findByCompanyUuidAndPayerIdAndDeletedIsFalseOrderByCreatedAtDesc(currentCompany(), payerId, pageable);
        return ApiResponse.success(page.map(vehicleEntryMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<VehicleEntryResponseDto>> getAllLedgers(Pageable pageable) {
        Page<VehicleEntry> page = vehicleEntryRepository
                .findByCompanyUuidAndDeletedIsFalseOrderByCreatedAtDesc(currentCompany(), pageable);
        return ApiResponse.success(page.map(vehicleEntryMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<PayerLedgerSummaryDto>> getSummary() {
        List<PayerLedgerSummary> rows = vehicleEntryRepository.summarizePendingByPayer(currentCompany());
        List<PayerLedgerSummaryDto> dtos = rows.stream()
                .map(r -> new PayerLedgerSummaryDto(r.getPayerId(), r.getPayerName(), r.getPendingAmt()))
                .collect(Collectors.toList());
        return ApiResponse.success(dtos);
    }

    @Override
    public ApiResponse<Void> applyPayment(String payerId, ApplyPaymentRequest request) {
        BigDecimal amount = request.getAmount();
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        OffsetDateTime now = OffsetDateTime.now();

        // record the settlement
        PayerSettlement settlement = new PayerSettlement();
        settlement.setSettlementId(codeGenerator.generate("PS"));
        settlement.setPayerId(payerId);
        settlement.setAmount(amount);
        settlement.setDate(now);
        settlement.setCompanyUuid(currentCompany());
        settlement.setIsSynced(true);
        settlement.setCreatedAt(now);
        settlement.setUpdatedAt(now);
        settlement.setDeleted(false);
        payerSettlementRepository.save(settlement);

        // apply to ledger entries FIFO
        List<VehicleEntry> entries = vehicleEntryRepository
                .findByCompanyUuidAndPayerIdAndDeletedIsFalseAndIsSettledFalseOrderByEntryTimeAsc(currentCompany(), payerId);
        BigDecimal remaining = amount;
        for (VehicleEntry e : entries) {
            if (remaining.signum() <= 0) break;
            BigDecimal pending = e.getPendingAmt();
            if (pending == null || pending.signum() <= 0) continue;
            BigDecimal applied = remaining.min(pending);
            e.setPaidAmount(e.getPaidAmount().add(applied));
            e.setPendingAmt(pending.subtract(applied));
            if (e.getPendingAmt().compareTo(BigDecimal.ZERO) == 0) {
                e.setIsSettled(true);
                e.setSettlementType(request.getSettlementType());
                e.setSettlementDate(now);
            }
            remaining = remaining.subtract(applied);
        }
        vehicleEntryRepository.saveAll(entries);
        return ApiResponse.success(null);
    }
}

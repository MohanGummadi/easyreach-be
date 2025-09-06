package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.ledger.ApplyPaymentRequest;
import com.easyreach.backend.dto.ledger.PayerLedgerSummaryDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PayerLedgerService {
    ApiResponse<Page<VehicleEntryResponseDto>> getLedgerForPayer(String payerId, Pageable pageable);
    ApiResponse<Page<VehicleEntryResponseDto>> getAllLedgers(Pageable pageable);
    ApiResponse<List<PayerLedgerSummaryDto>> getSummary();
    ApiResponse<Void> applyPayment(String payerId, ApplyPaymentRequest request);
}

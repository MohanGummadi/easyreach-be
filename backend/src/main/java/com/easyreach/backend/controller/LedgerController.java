package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.ledger.ApplyPaymentRequest;
import com.easyreach.backend.dto.ledger.PayerLedgerSummaryDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.service.PayerLedgerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ledger")
@RequiredArgsConstructor
@Tag(name = "Ledger")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class LedgerController {

    private final PayerLedgerService service;

    @GetMapping("/{payerId}")
    @Operation(summary = "Get ledger for payer")
    public ResponseEntity<ApiResponse<Page<VehicleEntryResponseDto>>> getLedgerForPayer(@PathVariable String payerId, Pageable pageable) {
        return ResponseEntity.ok(service.getLedgerForPayer(payerId, pageable));
    }

    @GetMapping
    @Operation(summary = "Get all ledgers")
    public ResponseEntity<ApiResponse<Page<VehicleEntryResponseDto>>> getAllLedger(Pageable pageable) {
        return ResponseEntity.ok(service.getAllLedgers(pageable));
    }

    @GetMapping("/summary")
    @Operation(summary = "Ledger summary by payer")
    public ResponseEntity<ApiResponse<List<PayerLedgerSummaryDto>>> summary() {
        return ResponseEntity.ok(service.getSummary());
    }

    @PostMapping("/{payerId}/apply-payment")
    @Operation(summary = "Apply payment to payer ledger")
    public ResponseEntity<ApiResponse<Void>> applyPayment(@PathVariable String payerId, @RequestBody ApplyPaymentRequest req) {
        return ResponseEntity.ok(service.applyPayment(payerId, req));
    }
}

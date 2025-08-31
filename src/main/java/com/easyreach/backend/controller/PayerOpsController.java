package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.service.PayerQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payers-ops")
@RequiredArgsConstructor
@Tag(name = "PayerOps")
public class PayerOpsController {

    private final PayerQueryService service;

    @GetMapping("/search")
    @Operation(summary = "Search active payers by name within company")
    public ResponseEntity<ApiResponse<Page<PayerResponseDto>>> search(@RequestParam String companyUuid,
                                                                      @RequestParam(required = false) String q,
                                                                      Pageable pageable) {
        return ResponseEntity.ok(service.searchActive(companyUuid, q, pageable));
    }

    @DeleteMapping("/{payerId}/soft")
    @Operation(summary = "Soft delete payer (set deleted_at)")
    public ResponseEntity<ApiResponse<Void>> softDelete(@PathVariable String payerId) {
        return ResponseEntity.ok(service.softDelete(payerId));
    }
}

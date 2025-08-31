package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementResponseDto;
import com.easyreach.backend.service.PayerSettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payer-settlements")
@RequiredArgsConstructor
@Tag(name="PayerSettlement")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class PayerSettlementController {
    private final PayerSettlementService service;

    @PostMapping
    @Operation(summary = "Create PayerSettlement")
    public ResponseEntity<ApiResponse<PayerSettlementResponseDto>> create(@Valid @RequestBody PayerSettlementRequestDto dto){
        log.info("Create PayerSettlement request: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating PayerSettlement with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update PayerSettlement")
    public ResponseEntity<ApiResponse<PayerSettlementResponseDto>> update(@PathVariable String id, @Valid @RequestBody PayerSettlementRequestDto dto){
        log.info("Update PayerSettlement id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating PayerSettlement id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete PayerSettlement")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete PayerSettlement id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting PayerSettlement id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get PayerSettlement")
    public ResponseEntity<ApiResponse<PayerSettlementResponseDto>> get(@PathVariable String id){
        log.info("Get PayerSettlement id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting PayerSettlement id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List PayerSettlement")
    public ResponseEntity<ApiResponse<Page<PayerSettlementResponseDto>>> list(Pageable pageable){
        log.info("List PayerSettlement with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing PayerSettlement", e);
            throw e;
        }
    }
}

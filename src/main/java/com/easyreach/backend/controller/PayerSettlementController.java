package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementResponseDto;
import com.easyreach.backend.service.PayerSettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payer-settlements")
@RequiredArgsConstructor
@Tag(name="PayerSettlement")
public class PayerSettlementController {
    private final PayerSettlementService service;

    @PostMapping
    @Operation(summary = "Create PayerSettlement")
    public ResponseEntity<ApiResponse<PayerSettlementResponseDto>> create(@Valid @RequestBody PayerSettlementRequestDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update PayerSettlement")
    public ResponseEntity<ApiResponse<PayerSettlementResponseDto>> update(@PathVariable String id, @Valid @RequestBody PayerSettlementRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete PayerSettlement")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get PayerSettlement")
    public ResponseEntity<ApiResponse<PayerSettlementResponseDto>> get(@PathVariable String id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "List PayerSettlement")
    public ResponseEntity<ApiResponse<Page<PayerSettlementResponseDto>>> list(Pageable pageable){
        return ResponseEntity.ok(service.list(pageable));
    }
}

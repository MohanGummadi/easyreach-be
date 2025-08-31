package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.service.PayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payers")
@RequiredArgsConstructor
@Tag(name="Payer")
public class PayerController {
    private final PayerService service;

    @PostMapping
    @Operation(summary = "Create Payer")
    public ResponseEntity<ApiResponse<PayerResponseDto>> create(@Valid @RequestBody PayerRequestDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Payer")
    public ResponseEntity<ApiResponse<PayerResponseDto>> update(@PathVariable String id, @Valid @RequestBody PayerRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Payer")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Payer")
    public ResponseEntity<ApiResponse<PayerResponseDto>> get(@PathVariable String id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "List Payer")
    public ResponseEntity<ApiResponse<Page<PayerResponseDto>>> list(Pageable pageable){
        return ResponseEntity.ok(service.list(pageable));
    }
}

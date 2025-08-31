package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.service.PayerService;
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
@RequestMapping("/api/payers")
@RequiredArgsConstructor
@Tag(name="Payer")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class PayerController {
    private final PayerService service;

    @PostMapping
    @Operation(summary = "Create Payer")
    public ResponseEntity<ApiResponse<PayerResponseDto>> create(@Valid @RequestBody PayerRequestDto dto){
        log.info("Create Payer request: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating Payer with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Payer")
    public ResponseEntity<ApiResponse<PayerResponseDto>> update(@PathVariable String id, @Valid @RequestBody PayerRequestDto dto){
        log.info("Update Payer id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating Payer id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Payer")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete Payer id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting Payer id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Payer")
    public ResponseEntity<ApiResponse<PayerResponseDto>> get(@PathVariable String id){
        log.info("Get Payer id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting Payer id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List Payer")
    public ResponseEntity<ApiResponse<Page<PayerResponseDto>>> list(Pageable pageable){
        log.info("List Payers with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing Payers", e);
            throw e;
        }
    }
}

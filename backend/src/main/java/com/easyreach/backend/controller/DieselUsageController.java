package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.dto.diesel_usage.DieselUsageResponseDto;
import com.easyreach.backend.service.DieselUsageService;
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
@RequestMapping("/api/diesel-usage")
@RequiredArgsConstructor
@Tag(name="DieselUsage")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class DieselUsageController {
    private final DieselUsageService service;

    @PostMapping
    @Operation(summary = "Create DieselUsage")
    public ResponseEntity<ApiResponse<DieselUsageResponseDto>> create(@Valid @RequestBody DieselUsageRequestDto dto){
        log.info("Create DieselUsage request: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating DieselUsage with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update DieselUsage")
    public ResponseEntity<ApiResponse<DieselUsageResponseDto>> update(@PathVariable String id, @Valid @RequestBody DieselUsageRequestDto dto){
        log.info("Update DieselUsage id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating DieselUsage id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete DieselUsage")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete DieselUsage id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting DieselUsage id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get DieselUsage")
    public ResponseEntity<ApiResponse<DieselUsageResponseDto>> get(@PathVariable String id){
        log.info("Get DieselUsage id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting DieselUsage id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List DieselUsage")
    public ResponseEntity<ApiResponse<Page<DieselUsageResponseDto>>> list(Pageable pageable){
        log.info("List DieselUsage with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing DieselUsage", e);
            throw e;
        }
    }
}

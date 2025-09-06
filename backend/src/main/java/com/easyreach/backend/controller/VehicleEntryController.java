package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.service.VehicleEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle-entries")
@RequiredArgsConstructor
@Tag(name="VehicleEntry")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class VehicleEntryController {
    private final VehicleEntryService service;

    @PostMapping
    @Operation(summary = "Create VehicleEntry")
    public ResponseEntity<ApiResponse<VehicleEntryResponseDto>> create(@Valid @RequestBody VehicleEntryRequestDto dto){
        log.info("Create VehicleEntry request: {}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating VehicleEntry with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update VehicleEntry")
    public ResponseEntity<ApiResponse<VehicleEntryResponseDto>> update(@PathVariable String id, @Valid @RequestBody VehicleEntryRequestDto dto){
        log.info("Update VehicleEntry id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating VehicleEntry id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete VehicleEntry")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete VehicleEntry id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting VehicleEntry id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get VehicleEntry")
    public ResponseEntity<ApiResponse<VehicleEntryResponseDto>> get(@PathVariable String id){
        log.info("Get VehicleEntry id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting VehicleEntry id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List VehicleEntry")
    public ResponseEntity<ApiResponse<Page<VehicleEntryResponseDto>>> list(Pageable pageable){
        log.info("List VehicleEntry with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing VehicleEntry", e);
            throw e;
        }
    }
}

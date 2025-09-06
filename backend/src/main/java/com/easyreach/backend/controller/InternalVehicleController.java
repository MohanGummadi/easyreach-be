package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleRequestDto;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleResponseDto;
import com.easyreach.backend.service.InternalVehicleService;
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
@RequestMapping("/api/internal-vehicles")
@RequiredArgsConstructor
@Tag(name="InternalVehicle")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class InternalVehicleController {
    private final InternalVehicleService service;

    @PostMapping
    @Operation(summary = "Create InternalVehicle")
    public ResponseEntity<ApiResponse<InternalVehicleResponseDto>> create(@Valid @RequestBody InternalVehicleRequestDto dto){
        log.info("Create InternalVehicle request: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating InternalVehicle with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update InternalVehicle")
    public ResponseEntity<ApiResponse<InternalVehicleResponseDto>> update(@PathVariable String id, @Valid @RequestBody InternalVehicleRequestDto dto){
        log.info("Update InternalVehicle id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating InternalVehicle id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete InternalVehicle")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete InternalVehicle id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting InternalVehicle id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get InternalVehicle")
    public ResponseEntity<ApiResponse<InternalVehicleResponseDto>> get(@PathVariable String id){
        log.info("Get InternalVehicle id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting InternalVehicle id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List InternalVehicle")
    public ResponseEntity<ApiResponse<Page<InternalVehicleResponseDto>>> list(Pageable pageable){
        log.info("List InternalVehicle with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing InternalVehicle", e);
            throw e;
        }
    }
}

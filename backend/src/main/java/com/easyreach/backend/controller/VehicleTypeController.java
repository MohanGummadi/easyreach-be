package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeRequestDto;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeResponseDto;
import com.easyreach.backend.service.VehicleTypeService;
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
@RequestMapping("/api/vehicle-types")
@RequiredArgsConstructor
@Tag(name="VehicleType")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class VehicleTypeController {
    private final VehicleTypeService service;

    @PostMapping
    @Operation(summary = "Create VehicleType")
    public ResponseEntity<ApiResponse<VehicleTypeResponseDto>> create(@Valid @RequestBody VehicleTypeRequestDto dto){
        log.info("Create VehicleType request: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating VehicleType with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update VehicleType")
    public ResponseEntity<ApiResponse<VehicleTypeResponseDto>> update(@PathVariable String id, @Valid @RequestBody VehicleTypeRequestDto dto){
        log.info("Update VehicleType id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating VehicleType id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete VehicleType")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete VehicleType id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting VehicleType id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get VehicleType")
    public ResponseEntity<ApiResponse<VehicleTypeResponseDto>> get(@PathVariable String id){
        log.info("Get VehicleType id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting VehicleType id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List VehicleType")
    public ResponseEntity<ApiResponse<Page<VehicleTypeResponseDto>>> list(Pageable pageable){
        log.info("List VehicleType with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing VehicleType", e);
            throw e;
        }
    }
}

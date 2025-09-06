package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageRequestDto;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageResponseDto;
import com.easyreach.backend.service.EquipmentUsageService;
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
@RequestMapping("/api/equipment-usage")
@RequiredArgsConstructor
@Tag(name="EquipmentUsage")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class EquipmentUsageController {
    private final EquipmentUsageService service;

    @PostMapping
    @Operation(summary = "Create EquipmentUsage")
    public ResponseEntity<ApiResponse<EquipmentUsageResponseDto>> create(@Valid @RequestBody EquipmentUsageRequestDto dto){
        log.info("Create EquipmentUsage request: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating EquipmentUsage with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update EquipmentUsage")
    public ResponseEntity<ApiResponse<EquipmentUsageResponseDto>> update(@PathVariable String id, @Valid @RequestBody EquipmentUsageRequestDto dto){
        log.info("Update EquipmentUsage id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating EquipmentUsage id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete EquipmentUsage")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete EquipmentUsage id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting EquipmentUsage id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get EquipmentUsage")
    public ResponseEntity<ApiResponse<EquipmentUsageResponseDto>> get(@PathVariable String id){
        log.info("Get EquipmentUsage id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting EquipmentUsage id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List EquipmentUsage")
    public ResponseEntity<ApiResponse<Page<EquipmentUsageResponseDto>>> list(Pageable pageable){
        log.info("List EquipmentUsage with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing EquipmentUsage", e);
            throw e;
        }
    }
}

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipment-usage")
@RequiredArgsConstructor
@Tag(name="EquipmentUsage")
@SecurityRequirement(name = "bearerAuth")
public class EquipmentUsageController {
    private final EquipmentUsageService service;

    @PostMapping
    @Operation(summary = "Create EquipmentUsage")
    public ResponseEntity<ApiResponse<EquipmentUsageResponseDto>> create(@Valid @RequestBody EquipmentUsageRequestDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update EquipmentUsage")
    public ResponseEntity<ApiResponse<EquipmentUsageResponseDto>> update(@PathVariable String id, @Valid @RequestBody EquipmentUsageRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete EquipmentUsage")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get EquipmentUsage")
    public ResponseEntity<ApiResponse<EquipmentUsageResponseDto>> get(@PathVariable String id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "List EquipmentUsage")
    public ResponseEntity<ApiResponse<Page<EquipmentUsageResponseDto>>> list(Pageable pageable){
        return ResponseEntity.ok(service.list(pageable));
    }
}

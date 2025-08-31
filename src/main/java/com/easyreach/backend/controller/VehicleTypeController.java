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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle-types")
@RequiredArgsConstructor
@Tag(name="VehicleType")
@SecurityRequirement(name = "bearerAuth")
public class VehicleTypeController {
    private final VehicleTypeService service;

    @PostMapping
    @Operation(summary = "Create VehicleType")
    public ResponseEntity<ApiResponse<VehicleTypeResponseDto>> create(@Valid @RequestBody VehicleTypeRequestDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update VehicleType")
    public ResponseEntity<ApiResponse<VehicleTypeResponseDto>> update(@PathVariable String id, @Valid @RequestBody VehicleTypeRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete VehicleType")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get VehicleType")
    public ResponseEntity<ApiResponse<VehicleTypeResponseDto>> get(@PathVariable String id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "List VehicleType")
    public ResponseEntity<ApiResponse<Page<VehicleTypeResponseDto>>> list(Pageable pageable){
        return ResponseEntity.ok(service.list(pageable));
    }
}

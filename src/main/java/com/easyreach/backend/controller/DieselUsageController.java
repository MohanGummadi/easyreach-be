package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.dto.diesel_usage.DieselUsageResponseDto;
import com.easyreach.backend.service.DieselUsageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diesel-usage")
@RequiredArgsConstructor
@Tag(name="DieselUsage")
public class DieselUsageController {
    private final DieselUsageService service;

    @PostMapping
    @Operation(summary = "Create DieselUsage")
    public ResponseEntity<ApiResponse<DieselUsageResponseDto>> create(@Valid @RequestBody DieselUsageRequestDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update DieselUsage")
    public ResponseEntity<ApiResponse<DieselUsageResponseDto>> update(@PathVariable String id, @Valid @RequestBody DieselUsageRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete DieselUsage")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get DieselUsage")
    public ResponseEntity<ApiResponse<DieselUsageResponseDto>> get(@PathVariable String id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "List DieselUsage")
    public ResponseEntity<ApiResponse<Page<DieselUsageResponseDto>>> list(Pageable pageable){
        return ResponseEntity.ok(service.list(pageable));
    }
}

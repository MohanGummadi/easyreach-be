package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;
import com.easyreach.backend.service.CompanyService;
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
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name="Company")
@SecurityRequirement(name = "bearerAuth")
public class CompanyController {
    private final CompanyService service;

    @PostMapping
    @Operation(summary = "Create Company")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> create(@Valid @RequestBody CompanyRequestDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Company")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> update(@PathVariable String id, @Valid @RequestBody CompanyRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Company")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Company")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> get(@PathVariable String id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "List Company")
    public ResponseEntity<ApiResponse<Page<CompanyResponseDto>>> list(Pageable pageable){
        return ResponseEntity.ok(service.list(pageable));
    }
}

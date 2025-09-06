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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name="Company")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class CompanyController {
    private final CompanyService service;

    @PostMapping
    @Operation(summary = "Create Company")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> create(@Valid @RequestBody CompanyRequestDto dto){
        log.info("Create Company request: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating Company with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Company")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> update(@PathVariable String id, @Valid @RequestBody CompanyRequestDto dto){
        log.info("Update Company id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating Company id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Company")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete Company id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting Company id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Company")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> get(@PathVariable String id){
        log.info("Get Company id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting Company id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List Company")
    public ResponseEntity<ApiResponse<Page<CompanyResponseDto>>> list(Pageable pageable){
        log.info("List Company with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing Company", e);
            throw e;
        }
    }
}

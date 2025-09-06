package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.expense_master.ExpenseMasterRequestDto;
import com.easyreach.backend.dto.expense_master.ExpenseMasterResponseDto;
import com.easyreach.backend.service.ExpenseMasterService;
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
@RequestMapping("/api/expense-master")
@RequiredArgsConstructor
@Tag(name="ExpenseMaster")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class ExpenseMasterController {
    private final ExpenseMasterService service;

    @PostMapping
    @Operation(summary = "Create ExpenseMaster")
    public ResponseEntity<ApiResponse<ExpenseMasterResponseDto>> create(@Valid @RequestBody ExpenseMasterRequestDto dto){
        log.info("Create ExpenseMaster request: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating ExpenseMaster with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update ExpenseMaster")
    public ResponseEntity<ApiResponse<ExpenseMasterResponseDto>> update(@PathVariable String id, @Valid @RequestBody ExpenseMasterRequestDto dto){
        log.info("Update ExpenseMaster id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating ExpenseMaster id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete ExpenseMaster")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete ExpenseMaster id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting ExpenseMaster id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ExpenseMaster")
    public ResponseEntity<ApiResponse<ExpenseMasterResponseDto>> get(@PathVariable String id){
        log.info("Get ExpenseMaster id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting ExpenseMaster id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List ExpenseMaster")
    public ResponseEntity<ApiResponse<Page<ExpenseMasterResponseDto>>> list(Pageable pageable){
        log.info("List ExpenseMaster with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing ExpenseMaster", e);
            throw e;
        }
    }
}

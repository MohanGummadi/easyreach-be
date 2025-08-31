package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.expense_master.ExpenseMasterRequestDto;
import com.easyreach.backend.dto.expense_master.ExpenseMasterResponseDto;
import com.easyreach.backend.service.ExpenseMasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense-master")
@RequiredArgsConstructor
@Tag(name="ExpenseMaster")
public class ExpenseMasterController {
    private final ExpenseMasterService service;

    @PostMapping
    @Operation(summary = "Create ExpenseMaster")
    public ResponseEntity<ApiResponse<ExpenseMasterResponseDto>> create(@Valid @RequestBody ExpenseMasterRequestDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update ExpenseMaster")
    public ResponseEntity<ApiResponse<ExpenseMasterResponseDto>> update(@PathVariable String id, @Valid @RequestBody ExpenseMasterRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete ExpenseMaster")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ExpenseMaster")
    public ResponseEntity<ApiResponse<ExpenseMasterResponseDto>> get(@PathVariable String id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "List ExpenseMaster")
    public ResponseEntity<ApiResponse<Page<ExpenseMasterResponseDto>>> list(Pageable pageable){
        return ResponseEntity.ok(service.list(pageable));
    }
}

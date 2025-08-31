package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.users.UserRequestDto;
import com.easyreach.backend.dto.users.UserResponseDto;
import com.easyreach.backend.service.UserService;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name="User")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class UserController {
    private final UserService service;

    @PostMapping
    @Operation(summary = "Create User")
    public ResponseEntity<ApiResponse<UserResponseDto>> create(@Valid @RequestBody UserRequestDto dto){
        log.info("Create User request: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating User with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User")
    public ResponseEntity<ApiResponse<UserResponseDto>> update(@PathVariable String id, @Valid @RequestBody UserRequestDto dto){
        log.info("Update User id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating User id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete User id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting User id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User")
    public ResponseEntity<ApiResponse<UserResponseDto>> get(@PathVariable String id){
        log.info("Get User id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting User id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List User")
    public ResponseEntity<ApiResponse<Page<UserResponseDto>>> list(Pageable pageable){
        log.info("List User with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing User", e);
            throw e;
        }
    }
}

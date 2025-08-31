package com.easyreach.backend.exception;

import com.easyreach.backend.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> notFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> invalid(MethodArgumentNotValidException ex) {
        List<String> errs = ex.getBindingResult().getAllErrors().stream().map(e -> {
            if (e instanceof FieldError fe) return fe.getField() + ": " + fe.getDefaultMessage();
            return e.getDefaultMessage();
        }).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(ApiResponse.failure(errs));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> illegal(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> other(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.failure("Unexpected error: " + ex.getMessage()));
    }
}

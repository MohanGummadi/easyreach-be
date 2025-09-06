package com.easyreach.backend.exception;

import com.easyreach.backend.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void notFound_returns404() {
        ResponseEntity<ApiResponse<Object>> res = handler.notFound(new EntityNotFoundException("missing"));
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void illegal_returnsBadRequest() {
        ResponseEntity<ApiResponse<Object>> res = handler.illegal(new IllegalArgumentException("bad"));
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void other_returns500() {
        ResponseEntity<ApiResponse<Object>> res = handler.other(new RuntimeException("boom"));
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

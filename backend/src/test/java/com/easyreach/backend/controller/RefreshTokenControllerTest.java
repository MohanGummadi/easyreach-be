package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.refresh_token.RefreshTokenRequestDto;
import com.easyreach.backend.dto.refresh_token.RefreshTokenResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RefreshTokenController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class RefreshTokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefreshTokenService refreshTokenService;

    private static final String VALID_BODY = "{\"jti\":\"j1\",\"userId\":\"u1\",\"issuedAt\":\"2023-01-01T00:00:00Z\",\"expiresAt\":\"2023-01-02T00:00:00Z\"}";

    @Test
    @DisplayName("get refresh token – success")
    void getSuccess() throws Exception {
        RefreshTokenResponseDto dto = new RefreshTokenResponseDto();
        dto.setJti("j1");
        when(refreshTokenService.get("j1")).thenReturn(ApiResponse.success(dto));

        mockMvc.perform(get("/api/refresh-token/j1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.jti").value("j1"));

        verify(refreshTokenService).get("j1");
    }

    @Test
    @DisplayName("get refresh token – not found")
    void getNotFound() throws Exception {
        when(refreshTokenService.get("missing")).thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(get("/api/refresh-token/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("not found"));

        verify(refreshTokenService).get("missing");
    }

    @Test
    @DisplayName("create refresh token – bad request")
    void createBadRequest() throws Exception {
        when(refreshTokenService.create(any(RefreshTokenRequestDto.class)))
                .thenThrow(new IllegalArgumentException("bad"));

        mockMvc.perform(post("/api/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("bad"));
    }

    @Test
    @DisplayName("delete refresh token – server error")
    void deleteServerError() throws Exception {
        when(refreshTokenService.delete("j1")).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(delete("/api/refresh-token/j1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: boom"));
    }
}

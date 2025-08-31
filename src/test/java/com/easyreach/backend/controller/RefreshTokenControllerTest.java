package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.refresh_token.RefreshTokenRequestDto;
import com.easyreach.backend.dto.refresh_token.RefreshTokenResponseDto;
import com.easyreach.backend.service.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RefreshTokenController.class)
@AutoConfigureMockMvc(addFilters = false)
class RefreshTokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefreshTokenService service;

    @Test
    void create_success() throws Exception {
        Mockito.when(service.create(any(RefreshTokenRequestDto.class)))
                .thenReturn(ApiResponse.success(new RefreshTokenResponseDto()));

        mockMvc.perform(post("/api/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"jti\":\"1\",\"userId\":\"u\",\"issuedAt\":\"2020-01-01T00:00:00Z\",\"expiresAt\":\"2020-01-02T00:00:00Z\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void create_validationError() throws Exception {
        mockMvc.perform(post("/api/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void get_notFound() throws Exception {
        Mockito.when(service.get("1")).thenThrow(new EntityNotFoundException("Not found"));

        mockMvc.perform(get("/api/refresh-token/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Not found"));
    }
}


package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.dto.diesel_usage.DieselUsageResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.DieselUsageService;
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

@WebMvcTest(DieselUsageController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class DieselUsageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DieselUsageService dieselUsageService;

    private static final String VALID_BODY = "{\"dieselUsageId\":\"d1\",\"vehicleName\":\"v1\",\"date\":\"2023-01-01T00:00:00Z\",\"liters\":1,\"companyUuid\":\"c1\",\"isSynced\":true,\"createdAt\":\"2023-01-01T00:00:00Z\",\"updatedAt\":\"2023-01-01T00:00:00Z\",\"deleted\":false}";

    @Test
    @DisplayName("get diesel usage – success")
    void getSuccess() throws Exception {
        DieselUsageResponseDto dto = new DieselUsageResponseDto();
        dto.setDieselUsageId("d1");
        when(dieselUsageService.get("d1")).thenReturn(ApiResponse.success(dto));

        mockMvc.perform(get("/api/diesel-usage/d1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.dieselUsageId").value("d1"));

        verify(dieselUsageService).get("d1");
    }

    @Test
    @DisplayName("get diesel usage – not found")
    void getNotFound() throws Exception {
        when(dieselUsageService.get("missing")).thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(get("/api/diesel-usage/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("not found"));

        verify(dieselUsageService).get("missing");
    }

    @Test
    @DisplayName("create diesel usage – bad request")
    void createBadRequest() throws Exception {
        when(dieselUsageService.create(any(DieselUsageRequestDto.class)))
                .thenThrow(new IllegalArgumentException("bad"));

        mockMvc.perform(post("/api/diesel-usage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("bad"));
    }

    @Test
    @DisplayName("delete diesel usage – server error")
    void deleteServerError() throws Exception {
        when(dieselUsageService.delete("d1")).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(delete("/api/diesel-usage/d1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: boom"));
    }
}

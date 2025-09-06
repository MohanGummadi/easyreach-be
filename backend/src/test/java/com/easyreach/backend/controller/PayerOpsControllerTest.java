package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.PayerQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PayerOpsController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class PayerOpsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayerQueryService payerQueryService;

    @Test
    @DisplayName("search payers – success")
    void searchSuccess() throws Exception {
        Page<PayerResponseDto> page = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        when(payerQueryService.searchActive(eq("c1"), eq("q"), any()))
                .thenReturn(ApiResponse.success(page));

        mockMvc.perform(get("/api/payers-ops/search")
                        .param("companyUuid", "c1")
                        .param("q", "q"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(payerQueryService).searchActive(eq("c1"), eq("q"), any());
    }

    @Test
    @DisplayName("search payers – not found")
    void searchNotFound() throws Exception {
        when(payerQueryService.searchActive(eq("c1"), eq("missing"), any()))
                .thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(get("/api/payers-ops/search")
                        .param("companyUuid", "c1")
                        .param("q", "missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("not found"));

        verify(payerQueryService).searchActive(eq("c1"), eq("missing"), any());
    }

    @Test
    @DisplayName("search payers – bad request")
    void searchBadRequest() throws Exception {
        when(payerQueryService.searchActive(eq("c1"), eq("q"), any()))
                .thenThrow(new IllegalArgumentException("bad"));

        mockMvc.perform(get("/api/payers-ops/search")
                        .param("companyUuid", "c1")
                        .param("q", "q"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("bad"));
    }

    @Test
    @DisplayName("soft delete – server error")
    void softDeleteServerError() throws Exception {
        when(payerQueryService.softDelete("p1")).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(delete("/api/payers-ops/p1/soft"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: boom"));
    }
}

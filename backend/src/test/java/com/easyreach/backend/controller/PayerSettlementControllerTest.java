package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.PayerSettlementService;
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

@WebMvcTest(PayerSettlementController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class PayerSettlementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayerSettlementService payerSettlementService;

    private static final String VALID_BODY = "{\"settlementId\":\"s1\",\"payerId\":\"p1\",\"amount\":10,\"date\":\"2023-01-01T00:00:00Z\",\"companyUuid\":\"c1\",\"isSynced\":true,\"createdAt\":\"2023-01-01T00:00:00Z\",\"updatedAt\":\"2023-01-01T00:00:00Z\",\"deleted\":false}";

    @Test
    @DisplayName("get payer settlement – success")
    void getSettlementSuccess() throws Exception {
        PayerSettlementResponseDto dto = new PayerSettlementResponseDto();
        dto.setSettlementId("s1");
        when(payerSettlementService.get("s1")).thenReturn(ApiResponse.success(dto));

        mockMvc.perform(get("/api/payer-settlements/s1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.settlementId").value("s1"));

        verify(payerSettlementService).get("s1");
    }

    @Test
    @DisplayName("get payer settlement – not found")
    void getSettlementNotFound() throws Exception {
        when(payerSettlementService.get("missing")).thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(get("/api/payer-settlements/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("not found"));

        verify(payerSettlementService).get("missing");
    }

    @Test
    @DisplayName("create payer settlement – bad request")
    void createSettlementBadRequest() throws Exception {
        when(payerSettlementService.create(any(PayerSettlementRequestDto.class)))
                .thenThrow(new IllegalArgumentException("bad"));

        mockMvc.perform(post("/api/payer-settlements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("bad"));
    }

    @Test
    @DisplayName("delete payer settlement – server error")
    void deleteSettlementServerError() throws Exception {
        when(payerSettlementService.delete("s1")).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(delete("/api/payer-settlements/s1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: boom"));
    }
}

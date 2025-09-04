package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.PayerService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PayerController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class PayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayerService payerService;

    private static final String VALID_BODY = "{\"payerId\":\"p1\",\"payerName\":\"Payer\",\"mobileNo\":\"123\",\"companyUuid\":\"c1\",\"isSynced\":true,\"createdAt\":\"2023-01-01T00:00:00Z\",\"updatedAt\":\"2023-01-01T00:00:00Z\",\"deleted\":false}";

    @Test
    @DisplayName("get payer – success")
    void getPayerSuccess() throws Exception {
        PayerResponseDto dto = new PayerResponseDto();
        dto.setPayerId("p1");
        when(payerService.get("p1")).thenReturn(ApiResponse.success(dto));

        mockMvc.perform(get("/api/payers/p1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.payerId").value("p1"));

        verify(payerService).get("p1");
    }

    @Test
    @DisplayName("get payer – not found")
    void getPayerNotFound() throws Exception {
        when(payerService.get("missing")).thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(get("/api/payers/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("not found"));

        verify(payerService).get("missing");
    }

    @Test
    @DisplayName("create payer – bad request")
    void createPayerBadRequest() throws Exception {
        when(payerService.create(any(PayerRequestDto.class)))
                .thenThrow(new IllegalArgumentException("bad"));

        mockMvc.perform(post("/api/payers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("bad"));
    }

    @Test
    @DisplayName("delete payer – server error")
    void deletePayerServerError() throws Exception {
        when(payerService.delete("p1")).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(delete("/api/payers/p1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: boom"));
    }
}

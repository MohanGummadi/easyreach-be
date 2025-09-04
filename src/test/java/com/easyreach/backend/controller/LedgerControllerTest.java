package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.ledger.ApplyPaymentRequest;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.PayerLedgerService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LedgerController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class LedgerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayerLedgerService payerLedgerService;

    @Test
    @DisplayName("get ledger for payer – success")
    void getLedgerSuccess() throws Exception {
        Page<VehicleEntryResponseDto> page = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        when(payerLedgerService.getLedgerForPayer(eq("p1"), any()))
                .thenReturn(ApiResponse.success(page));

        mockMvc.perform(get("/api/ledger/p1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(payerLedgerService).getLedgerForPayer(eq("p1"), any());
    }

    @Test
    @DisplayName("get ledger for payer – not found")
    void getLedgerNotFound() throws Exception {
        when(payerLedgerService.getLedgerForPayer(eq("missing"), any()))
                .thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(get("/api/ledger/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("not found"));

        verify(payerLedgerService).getLedgerForPayer(eq("missing"), any());
    }

    @Test
    @DisplayName("get all ledger – bad request")
    void getAllLedgerBadRequest() throws Exception {
        when(payerLedgerService.getAllLedgers(any()))
                .thenThrow(new IllegalArgumentException("bad request"));

        mockMvc.perform(get("/api/ledger"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("bad request"));
    }

    @Test
    @DisplayName("apply payment – server error")
    void applyPaymentServerError() throws Exception {
        when(payerLedgerService.applyPayment(eq("p1"), any()))
                .thenThrow(new RuntimeException("boom"));

        String body = "{\"amount\":10,\"settlementType\":\"CASH\"}";

        mockMvc.perform(post("/api/ledger/p1/apply-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: boom"));
    }
}

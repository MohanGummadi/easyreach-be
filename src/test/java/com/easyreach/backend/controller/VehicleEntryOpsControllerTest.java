package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.VehicleEntryOpsService;
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

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleEntryOpsController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class VehicleEntryOpsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleEntryOpsService vehicleEntryOpsService;

    @Test
    @DisplayName("add payment – success")
    void addPaymentSuccess() throws Exception {
        VehicleEntryResponseDto dto = new VehicleEntryResponseDto();
        when(vehicleEntryOpsService.addPayment(eq("e1"), eq(new BigDecimal("50")), eq("user"), any()))
                .thenReturn(ApiResponse.success(dto));

        String body = "{\"amount\":50,\"receivedBy\":\"user\"}";
        mockMvc.perform(post("/api/vehicle-entries-ops/e1/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(vehicleEntryOpsService).addPayment(eq("e1"), eq(new BigDecimal("50")), eq("user"), any());
    }

    @Test
    @DisplayName("exit – server error")
    void exitServerError() throws Exception {
        when(vehicleEntryOpsService.markExit(eq("e1"), any())).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(post("/api/vehicle-entries-ops/e1/exit"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: boom"));

        verify(vehicleEntryOpsService).markExit(eq("e1"), any());
    }

    @Test
    @DisplayName("add payment – not found")
    void addPaymentNotFound() throws Exception {
        when(vehicleEntryOpsService.addPayment(eq("missing"), any(), any(), any()))
                .thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(post("/api/vehicle-entries-ops/missing/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":10}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("not found"));
    }
}

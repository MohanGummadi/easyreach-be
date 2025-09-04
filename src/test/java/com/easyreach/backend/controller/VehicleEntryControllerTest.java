package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.VehicleEntryService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleEntryController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class VehicleEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleEntryService vehicleEntryService;

    @Test
    @DisplayName("get vehicle entry – success")
    void getVehicleEntrySuccess() throws Exception {
        VehicleEntryResponseDto dto = new VehicleEntryResponseDto();
        dto.setEntryId("v1");
        when(vehicleEntryService.get("v1")).thenReturn(ApiResponse.success(dto));

        mockMvc.perform(get("/api/vehicle-entries/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.entryId").value("v1"));

        verify(vehicleEntryService).get("v1");
    }

    @Test
    @DisplayName("get vehicle entry – not found")
    void getVehicleEntryNotFound() throws Exception {
        when(vehicleEntryService.get("missing")).thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(get("/api/vehicle-entries/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("not found"));

        verify(vehicleEntryService).get("missing");
    }

    @Test
    @DisplayName("create vehicle entry – validation error")
    void createVehicleEntryValidationError() throws Exception {
        mockMvc.perform(post("/api/vehicle-entries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}

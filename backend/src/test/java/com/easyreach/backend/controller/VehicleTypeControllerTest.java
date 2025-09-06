package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.VehicleTypeService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class VehicleTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleTypeService vehicleTypeService;

    @Test
    @DisplayName("get vehicle type – success")
    void getVehicleTypeSuccess() throws Exception {
        VehicleTypeResponseDto dto = new VehicleTypeResponseDto();
        dto.setId("t1");
        when(vehicleTypeService.get("t1")).thenReturn(ApiResponse.success(dto));

        mockMvc.perform(get("/api/vehicle-types/t1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value("t1"));

        verify(vehicleTypeService).get("t1");
    }

    @Test
    @DisplayName("get vehicle type – not found")
    void getVehicleTypeNotFound() throws Exception {
        when(vehicleTypeService.get("missing")).thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(get("/api/vehicle-types/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("not found"));

        verify(vehicleTypeService).get("missing");
    }

    @Test
    @DisplayName("create vehicle type – validation error")
    void createVehicleTypeValidationError() throws Exception {
        mockMvc.perform(post("/api/vehicle-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}

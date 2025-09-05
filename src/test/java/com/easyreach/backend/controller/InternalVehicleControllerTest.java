package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.InternalVehicleService;
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

@WebMvcTest(InternalVehicleController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class InternalVehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InternalVehicleService internalVehicleService;

    @Test
    @DisplayName("get internal vehicle – success")
    void getInternalVehicleSuccess() throws Exception {
        InternalVehicleResponseDto dto = new InternalVehicleResponseDto();
        dto.setVehicleId("i1");
        when(internalVehicleService.get("i1")).thenReturn(ApiResponse.success(dto));

        mockMvc.perform(get("/api/internal-vehicles/i1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.vehicleId").value("i1"));

        verify(internalVehicleService).get("i1");
    }

    @Test
    @DisplayName("delete internal vehicle – not found")
    void deleteInternalVehicleNotFound() throws Exception {
        when(internalVehicleService.delete("missing")).thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(delete("/api/internal-vehicles/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("not found"));

        verify(internalVehicleService).delete("missing");
    }

    @Test
    @DisplayName("create internal vehicle – validation error")
    void createInternalVehicleValidationError() throws Exception {
        mockMvc.perform(post("/api/internal-vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}

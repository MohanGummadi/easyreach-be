package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.service.VehicleEntryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleEntryController.class)
@AutoConfigureMockMvc(addFilters = false)
class VehicleEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleEntryService service;

    @Test
    void getVehicleEntry() throws Exception {
        Mockito.when(service.get("1")).thenReturn(ApiResponse.success(new VehicleEntryResponseDto()));

        mockMvc.perform(get("/api/vehicle-entries/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}

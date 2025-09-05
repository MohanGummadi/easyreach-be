package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.EquipmentUsageService;
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

@WebMvcTest(EquipmentUsageController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class EquipmentUsageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentUsageService equipmentUsageService;

    @Test
    @DisplayName("get equipment usage – success")
    void getEquipmentUsageSuccess() throws Exception {
        EquipmentUsageResponseDto dto = new EquipmentUsageResponseDto();
        dto.setEquipmentUsageId("u1");
        when(equipmentUsageService.get("u1")).thenReturn(ApiResponse.success(dto));

        mockMvc.perform(get("/api/equipment-usage/u1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.equipmentUsageId").value("u1"));

        verify(equipmentUsageService).get("u1");
    }

    @Test
    @DisplayName("delete equipment usage – server error")
    void deleteEquipmentUsageServerError() throws Exception {
        when(equipmentUsageService.delete("u1")).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(delete("/api/equipment-usage/u1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: boom"));
    }

    @Test
    @DisplayName("create equipment usage – validation error")
    void createEquipmentUsageValidationError() throws Exception {
        mockMvc.perform(post("/api/equipment-usage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}

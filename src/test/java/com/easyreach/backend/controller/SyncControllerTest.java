package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.SyncRequestDto;
import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SyncController.class)
@AutoConfigureMockMvc(addFilters = false)
class SyncControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private CompanyService companyService;
    @MockBean private DailyExpenseService dailyExpenseService;
    @MockBean private DieselUsageService dieselUsageService;
    @MockBean private EquipmentUsageService equipmentUsageService;
    @MockBean private ExpenseMasterService expenseMasterService;
    @MockBean private InternalVehicleService internalVehicleService;
    @MockBean private PayerService payerService;
    @MockBean private PayerSettlementService payerSettlementService;
    @MockBean private VehicleEntryService vehicleEntryService;
    @MockBean private VehicleTypeService vehicleTypeService;

    private CompanyRequestDto sampleCompany() {
        return new CompanyRequestDto(
                "u1","c1","name","123","coord","loc", LocalDate.now(),"owner","999","o@x.com",
                LocalDate.now(), true, true,"creator", OffsetDateTime.now(),"upd", OffsetDateTime.now()
        );
    }

    @Test
    void sync_populatedList_counts() throws Exception {
        Mockito.when(companyService.bulkSync(Mockito.anyList())).thenReturn(1);
        SyncRequestDto req = new SyncRequestDto();
        req.setCompanies(List.of(sampleCompany()));
        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(post("/api/sync").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.companies").value(1));
    }

    @Test
    void sync_emptyList_countsZero() throws Exception {
        SyncRequestDto req = new SyncRequestDto();
        req.setCompanies(List.of());
        String json = objectMapper.writeValueAsString(req);
        mockMvc.perform(post("/api/sync").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.companies").value(0));
    }

    @Test
    void sync_nullList_countsZero() throws Exception {
        SyncRequestDto req = new SyncRequestDto();
        String json = objectMapper.writeValueAsString(req);
        mockMvc.perform(post("/api/sync").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.companies").value(0));
    }
}

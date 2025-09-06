package com.easyreach.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.easyreach.backend.service.CompanyService;
import com.easyreach.backend.service.DailyExpenseService;
import com.easyreach.backend.service.DieselUsageService;
import com.easyreach.backend.service.EquipmentUsageService;
import com.easyreach.backend.service.ExpenseMasterService;
import com.easyreach.backend.service.InternalVehicleService;
import com.easyreach.backend.service.PayerService;
import com.easyreach.backend.service.PayerSettlementService;
import com.easyreach.backend.service.SyncDownloadService;
import com.easyreach.backend.service.VehicleEntryService;
import com.easyreach.backend.service.VehicleTypeService;

class SyncControllerIntegrationTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        CompanyService companyService = mock(CompanyService.class);
        DailyExpenseService dailyExpenseService = mock(DailyExpenseService.class);
        DieselUsageService dieselUsageService = mock(DieselUsageService.class);
        EquipmentUsageService equipmentUsageService = mock(EquipmentUsageService.class);
        ExpenseMasterService expenseMasterService = mock(ExpenseMasterService.class);
        InternalVehicleService internalVehicleService = mock(InternalVehicleService.class);
        PayerService payerService = mock(PayerService.class);
        PayerSettlementService payerSettlementService = mock(PayerSettlementService.class);
        VehicleEntryService vehicleEntryService = mock(VehicleEntryService.class);
        VehicleTypeService vehicleTypeService = mock(VehicleTypeService.class);
        SyncDownloadService syncDownloadService = mock(SyncDownloadService.class);

        when(companyService.bulkSync(any())).thenAnswer(inv -> ((List<?>) inv.getArgument(0)).size());
        when(dailyExpenseService.bulkSync(any())).thenAnswer(inv -> ((List<?>) inv.getArgument(0)).size());
        when(dieselUsageService.bulkSync(any())).thenAnswer(inv -> ((List<?>) inv.getArgument(0)).size());
        when(equipmentUsageService.bulkSync(any())).thenAnswer(inv -> ((List<?>) inv.getArgument(0)).size());
        when(expenseMasterService.bulkSync(any())).thenAnswer(inv -> ((List<?>) inv.getArgument(0)).size());
        when(internalVehicleService.bulkSync(any())).thenAnswer(inv -> ((List<?>) inv.getArgument(0)).size());
        when(payerService.bulkSync(any())).thenAnswer(inv -> ((List<?>) inv.getArgument(0)).size());
        when(payerSettlementService.bulkSync(any())).thenAnswer(inv -> ((List<?>) inv.getArgument(0)).size());
        when(vehicleEntryService.bulkSync(any())).thenAnswer(inv -> ((List<?>) inv.getArgument(0)).size());
        when(vehicleTypeService.bulkSync(any())).thenAnswer(inv -> ((List<?>) inv.getArgument(0)).size());

        SyncController controller = new SyncController(
                companyService,
                dailyExpenseService,
                dieselUsageService,
                equipmentUsageService,
                expenseMasterService,
                internalVehicleService,
                payerService,
                payerSettlementService,
                vehicleEntryService,
                vehicleTypeService,
                syncDownloadService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(new NoOpValidator())
                .build();
    }

    @Test
    void sync_returnsCountsAndNoErrors() throws Exception {
        String payload = """
            {
              "companies": [{}],
              "dailyExpenses": [{}],
              "dieselUsages": [{}],
              "equipmentUsages": [{}],
              "expenseMasters": [{}],
              "internalVehicles": [{}],
              "payers": [{}],
              "payerSettlements": [{}],
              "vehicleEntries": [{}],
              "vehicleTypes": [{}]
            }
            """;

        mockMvc.perform(post("/api/sync")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.companies").value(1))
                .andExpect(jsonPath("$.data.dailyExpenses").value(1))
                .andExpect(jsonPath("$.data.dieselUsages").value(1))
                .andExpect(jsonPath("$.data.equipmentUsages").value(1))
                .andExpect(jsonPath("$.data.expenseMasters").value(1))
                .andExpect(jsonPath("$.data.internalVehicles").value(1))
                .andExpect(jsonPath("$.data.payers").value(1))
                .andExpect(jsonPath("$.data.payerSettlements").value(1))
                .andExpect(jsonPath("$.data.vehicleEntries").value(1))
                .andExpect(jsonPath("$.data.vehicleTypes").value(1))
                .andExpect(jsonPath("$.errors").doesNotExist());
    }

    static class NoOpValidator implements Validator {
        @Override
        public boolean supports(Class<?> clazz) {
            return true;
        }

        @Override
        public void validate(Object target, Errors errors) {
            // no-op
        }
    }
}


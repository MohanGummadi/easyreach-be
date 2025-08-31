package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.service.PayerService;
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

@WebMvcTest(PayerController.class)
@AutoConfigureMockMvc(addFilters = false)
class PayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayerService service;

    @Test
    void getPayer() throws Exception {
        Mockito.when(service.get("1")).thenReturn(ApiResponse.success(new PayerResponseDto()));

        mockMvc.perform(get("/api/payers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}

package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;
import com.easyreach.backend.service.CompanyService;
import com.easyreach.backend.support.SampleJson;
import com.easyreach.backend.support.TestData;
import com.easyreach.backend.support.TestProfiles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CompanyController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestProfiles
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyService service;

    @Test
    void createReturnsOk() throws Exception {
        CompanyResponseDto respDto = TestData.companyResponse();
        when(service.create(any(CompanyRequestDto.class)))
                .thenReturn(ApiResponse.success(respDto));

        String json = objectMapper.writeValueAsString(TestData.companyRequest());

        mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.uuid").value(respDto.getUuid()));
    }

    @Test
    void createValidationError() throws Exception {
        mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SampleJson.EMPTY_OBJECT))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePropagatesErrors() throws Exception {
        when(service.update(Mockito.eq("1"), any(CompanyRequestDto.class)))
                .thenThrow(new RuntimeException("boom"));

        String json = objectMapper.writeValueAsString(TestData.companyRequest());
        mockMvc.perform(put("/api/companies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError());
    }
}

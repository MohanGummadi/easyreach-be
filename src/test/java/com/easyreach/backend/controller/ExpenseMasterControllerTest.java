package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.expense_master.ExpenseMasterResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.ExpenseMasterService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExpenseMasterController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class ExpenseMasterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseMasterService expenseMasterService;

    @Test
    @DisplayName("list expense master – success")
    void listExpenseMasterSuccess() throws Exception {
        Page<ExpenseMasterResponseDto> page = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        when(expenseMasterService.list(any())).thenReturn(ApiResponse.success(page));

        mockMvc.perform(get("/api/expense-master"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(expenseMasterService).list(any());
    }

    @Test
    @DisplayName("get expense master – not found")
    void getExpenseMasterNotFound() throws Exception {
        when(expenseMasterService.get("missing")).thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(get("/api/expense-master/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("not found"));

        verify(expenseMasterService).get("missing");
    }

    @Test
    @DisplayName("create expense master – validation error")
    void createExpenseMasterValidationError() throws Exception {
        mockMvc.perform(post("/api/expense-master")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}

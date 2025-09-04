package com.easyreach.backend.controller;

import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.ReceiptService;
import com.easyreach.backend.service.impl.ReceiptPdfService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReceiptFormController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class ReceiptFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptService receiptService;

    @MockBean
    private ReceiptPdfService receiptPdfService;

    @Test
    @DisplayName("new receipt form – success")
    void newFormSuccess() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "pass"));

        mockMvc.perform(get("/receipts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("receipts/receipt_form"))
                .andExpect(model().attributeExists("receipt"));
    }

    @Test
    @DisplayName("create receipt – bad request")
    void createReceiptBadRequest() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "pass"));
        when(receiptService.create(any())).thenThrow(new IllegalArgumentException("bad"));

        MockMultipartFile emptyFile = new MockMultipartFile("qrPng", new byte[0]);

        mockMvc.perform(multipart("/receipts")
                        .file(emptyFile)
                        .param("orderId", "o1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("bad"));
    }

    @Test
    @DisplayName("create receipt – server error")
    void createReceiptServerError() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "pass"));
        when(receiptService.create(any())).thenThrow(new RuntimeException("boom"));

        MockMultipartFile emptyFile = new MockMultipartFile("qrPng", new byte[0]);

        mockMvc.perform(multipart("/receipts")
                        .file(emptyFile)
                        .param("orderId", "o1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: boom"));
    }
}

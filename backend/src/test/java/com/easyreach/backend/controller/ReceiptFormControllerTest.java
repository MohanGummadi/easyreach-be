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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import com.easyreach.backend.repository.OrderRepository;
import com.easyreach.backend.entity.Order;
import com.easyreach.backend.entity.Receipt;
import java.util.Optional;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReceiptFormController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class ReceiptFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptService receiptService;

    @MockBean
    private ReceiptPdfService receiptPdfService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    @DisplayName("new receipt form – success")
    void newFormSuccess() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "pass"));
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/receipts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("receipts/receipt_form"))
                .andExpect(model().attributeExists("receipt"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    @DisplayName("create receipt – bad request")
    void createReceiptBadRequest() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "pass"));
        when(receiptService.create(any())).thenThrow(new IllegalArgumentException("bad"));
        when(orderRepository.findByOrderIdIgnoreCase(any())).thenReturn(java.util.Optional.of(Order.builder().qrUrl("qr").build()));

        mockMvc.perform(post("/receipts")
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
        when(orderRepository.findByOrderIdIgnoreCase(any())).thenReturn(Optional.of(Order.builder().qrUrl("qr").build()));

        mockMvc.perform(post("/receipts")
                        .param("orderId", "o1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: boom"));
    }

    @Test
    @DisplayName("create receipt – success uses order QR URL")
    void createReceiptSuccess() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "pass"));
        Order order = Order.builder().orderId("O1").qrUrl("qr-url").build();
        when(orderRepository.findByOrderIdIgnoreCase(any())).thenReturn(Optional.of(order));
        when(receiptService.create(any())).thenReturn(Receipt.builder().orderId("O1").tripNo("1").sandQuantity("10").build());
        when(receiptPdfService.buildReceiptPdf(any(), any())).thenReturn(new byte[]{1});

        mockMvc.perform(post("/receipts").param("orderId", "o1"))
                .andExpect(status().isOk());

        verify(receiptPdfService).buildReceiptPdf(any(), eq("qr-url"));
    }
}

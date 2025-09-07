package com.easyreach.backend.controller;

import com.easyreach.backend.dto.SandReceiptData;
import com.easyreach.backend.dto.receipt.ReceiptDto;
import com.easyreach.backend.service.ReceiptService;
import com.easyreach.backend.service.impl.ReceiptPdfService;
import com.easyreach.backend.repository.OrderRepository;
import com.easyreach.backend.entity.Order;
import com.easyreach.backend.entity.Receipt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class ReceiptFormController {

    private final ReceiptService receiptService;
    private final ReceiptPdfService receiptPdfService;
    private final OrderRepository orderRepository;

    private static final String FOOTER_LINE = "18.4060366,83.9543993 Thank you";

    @GetMapping("/receipts/new")
    public String newReceiptForm(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ReceiptDto dto = new ReceiptDto();
        dto.setCreatedBy(username);
        model.addAttribute("receipt", dto);
        model.addAttribute("orders", orderRepository.findAll());
        return "receipts/receipt_form";
    }

    @PostMapping(value = "/receipts")
    public ResponseEntity<byte[]> createReceipt(@ModelAttribute ReceiptDto dto) throws Exception {

        dto.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        Receipt receipt = receiptService.create(dto);

        Order order = orderRepository.findByOrderIdIgnoreCase(receipt.getOrderId())
                .orElseThrow();

        SandReceiptData data = new SandReceiptData();
        data.orderId = receipt.getOrderId();
        data.tripNo = receipt.getTripNo();
        data.customerName = order.getCustomerName();
        data.customerMobile = order.getCustomerMobile();
        data.sandQuantity = receipt.getSandQuantity();
        data.dispatchDateTime = dto.getDispatchDateTime();
        data.driverName = dto.getDriverName();
        data.driverMobile = dto.getDriverMobile();
        data.vehicleNo = dto.getVehicleNo();
        data.address = order.getFullAddress();
        data.footerLine = FOOTER_LINE;

        byte[] pdf = receiptPdfService.buildReceiptPdf(
                data,
                order.getQrUrl()
        );

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String sanitizedVehicleNo = dto.getVehicleNo() != null ? dto.getVehicleNo().replaceAll("[^A-Za-z0-9]", "") : "unknown";
        String fileName = "Receipt_" + sanitizedVehicleNo + "_" + timestamp + ".pdf";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}


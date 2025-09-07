package com.easyreach.backend.controller;

import com.easyreach.backend.dto.SandReceiptData;
import com.easyreach.backend.dto.receipt.ReceiptDto;
import com.easyreach.backend.service.ReceiptService;
import com.easyreach.backend.service.impl.ReceiptPdfService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.easyreach.backend.repository.OrderRepository;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
    private final ReceiptPdfService pdfService;
    private final ObjectMapper objectMapper;
    private final ReceiptService receiptService;
    private final OrderRepository orderRepository;

    public ReceiptController(ReceiptPdfService pdfService, ObjectMapper objectMapper, ReceiptService receiptService,
                             OrderRepository orderRepository) {
        this.pdfService = pdfService;
        this.objectMapper = objectMapper;
        this.receiptService = receiptService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> findByOrderId(@PathVariable String orderId) {
        orderId = orderId.toUpperCase();
        ReceiptDto dto = receiptService.findByOrderId(orderId);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No order id found");
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> buildPdf(
            @RequestPart("data") String dataJson
    ) throws Exception {

        SandReceiptData data = objectMapper.readValue(dataJson, SandReceiptData.class);

        var order = orderRepository.findByOrderIdIgnoreCase(data.orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // ✅ Generate PDF
        byte[] pdfBytes = pdfService.buildReceiptPdf(
                data,
                order.getQrUrl()
        );

        // ✅ Save PDF to folder (e.g., ./receipts)
        Path folder = Paths.get("./receipts");
        Files.createDirectories(folder);

        // Unique filename with sanitized vehicle number + timestamp
        String timestamp = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String sanitizedVehicleNo = data.vehicleNo != null ? data.vehicleNo.replaceAll("[^A-Za-z0-9]", "") : "unknown";
        String fileName = "Receipt_" + sanitizedVehicleNo + "_" + timestamp + ".pdf";
        Path savedFile = folder.resolve(fileName);

        try {
            Files.write(savedFile, pdfBytes);
        } catch (IOException e) {
            System.err.println("❌ Failed to save PDF: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save receipt PDF");
        }

        logger.info("PDF saved at: {}", savedFile.toFile().getAbsolutePath());

        // ✅ Return PDF in response as well
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}

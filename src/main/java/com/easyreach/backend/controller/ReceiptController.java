package com.easyreach.backend.controller;

import com.easyreach.backend.dto.SandReceiptData;
import com.easyreach.backend.service.impl.ReceiptPdfService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptPdfService pdfService = new ReceiptPdfService();

    @PostMapping(value = "/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> buildPdf(
            @RequestPart("data") String dataJson,
            @RequestPart(value = "qrPng", required = false) MultipartFile qrPng,
            @RequestPart(value = "qrUrl", required = false) String qrUrl
    ) throws Exception {

        // ✅ Fix: LocalDateTime parsing with JavaTimeModule
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        SandReceiptData data = mapper.readValue(dataJson, SandReceiptData.class);

        // ✅ Generate PDF
        byte[] pdfBytes = pdfService.buildReceiptPdf(
                data,
                (qrPng != null && !qrPng.isEmpty()) ? qrPng.getBytes() : null,
                qrUrl
        );

        // ✅ Save PDF to folder (e.g., ./receipts)
        Path folder = Paths.get("./receipts");
        Files.createDirectories(folder);

        // Unique filename with sanitized orderId + timestamp
        String timestamp = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String sanitizedOrderId = data.orderId != null ? data.orderId.replaceAll("[^A-Za-z0-9]", "") : "unknown";
        String fileName = "Receipt_" + sanitizedOrderId + "_" + timestamp + ".pdf";
        Path savedFile = folder.resolve(fileName);

        try {
            Files.write(savedFile, pdfBytes);
        } catch (IOException e) {
            System.err.println("❌ Failed to save PDF: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save receipt PDF");
        }

        System.out.println("✅ PDF saved at: " + savedFile.toAbsolutePath());

        // ✅ Return PDF in response as well
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}

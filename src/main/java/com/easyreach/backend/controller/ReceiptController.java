package com.easyreach.backend.controller;

import com.easyreach.backend.dto.SandReceiptData;
import com.easyreach.backend.service.impl.ReceiptPdfService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
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
        File folder = new File("./receipts");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Unique filename with orderId + timestamp
        String timestamp = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "Receipt_" + data.orderId + "_" + timestamp + ".pdf";
        File savedFile = new File(folder, fileName);

        try (FileOutputStream fos = new FileOutputStream(savedFile)) {
            fos.write(pdfBytes);
        }

        System.out.println("✅ PDF saved at: " + savedFile.getAbsolutePath());

        // ✅ Return PDF in response as well
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}

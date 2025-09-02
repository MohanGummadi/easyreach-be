package com.easyreach.backend.service;

import com.easyreach.backend.dto.SandReceiptData;
import com.easyreach.backend.service.impl.ReceiptPdfService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptPdfServiceTest {

    @Test
    public void generatesPdfFromTemplate() throws Exception {
        SandReceiptData d = new SandReceiptData();
        d.orderId = "OID123";
        d.tripNo = "TRIP1";
        d.customerName = "John";
        d.customerMobile = "1234567890";
        d.sandQuantity = "10";
        d.supplyPoint = "Point";
        d.dispatchDateTime = LocalDateTime.of(2024, 1, 1, 10, 30);
        d.driverName = "Driver";
        d.driverMobile = "0987654321";
        d.vehicleNo = "AP09AB1234";
        d.address = "Some address";
        d.footerLine = "Thank you";

        ReceiptPdfService svc = new ReceiptPdfService();
        byte[] pdf = svc.buildReceiptPdf(d, null, null);
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);

        try (PDDocument doc = PDDocument.load(new ByteArrayInputStream(pdf))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);
            assertTrue(text.contains("Consumer Copy"));
            assertTrue(text.contains("Driver Copy"));
            assertTrue(text.contains(d.orderId));
        }
    }
}

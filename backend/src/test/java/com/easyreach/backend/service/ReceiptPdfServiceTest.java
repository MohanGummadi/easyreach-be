package com.easyreach.backend.service;

import com.easyreach.backend.dto.SandReceiptData;
import com.easyreach.backend.service.impl.ReceiptPdfService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        d.dispatchDateTime = LocalDateTime.of(2024, 1, 1, 10, 30);
        d.driverName = "Driver";
        d.driverMobile = "0987654321";
        d.vehicleNo = "AP09AB1234";
        d.address = "Some address";
        d.footerLine = "18.4060366,83.9543993 Thank you";

        ReceiptPdfService svc = new ReceiptPdfService();
        byte[] pdf = svc.buildReceiptPdf(d, "https://example.com/qr");
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);

        try (PDDocument doc = PDDocument.load(new ByteArrayInputStream(pdf))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);
            assertTrue(text.contains("Consumer Copy"));
            assertTrue(text.contains("Driver Copy"));
            assertTrue(text.contains(d.orderId));
            assertTrue(text.contains("Consumer Name"));
            assertTrue(text.contains("Consumer Mobile"));
            assertTrue(text.contains("Khandyam"));
            assertTrue(text.contains("10.0Tons"));
            assertTrue(text.contains("18.4060366,83.9543993 Thank you"));
        }

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setCacheable(false);
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context ctx = new Context();
        List<Map<String, String>> rows = new ArrayList<>();
        rows.add(Map.of("label", "Trip No", "value", d.tripNo));
        rows.add(Map.of("label", "Sand Supply Point Name", "value", "Khandyam"));
        ctx.setVariable("rows", rows);
        ctx.setVariable("address", d.address);
        String html = engine.process("receipt", ctx);

        assertTrue(html.matches("(?s).*<td style=\"width:40%\">\\s*Trip No\\s*</td>\\s*<td style=\"width:60%;text-align:right;\">\\s*" + d.tripNo + "\\s*</td>.*"));
        assertTrue(html.matches("(?s).*<td style=\"width:60%\">\\s*Sand Supply Point Name\\s*</td>\\s*<td style=\"width:40%;text-align:right;\">\\s*Khandyam\\s*</td>.*"));
        assertTrue(html.contains("<div class=\"address-block\">Address: <span>" + d.address + "</span></div>"));
    }
}

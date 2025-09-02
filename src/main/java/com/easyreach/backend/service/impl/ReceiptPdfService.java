package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.SandReceiptData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

/**
 * Builds sand receipt PDF using Thymeleaf HTML template rendered with openhtmltopdf.
 */
public class ReceiptPdfService {

    private final TemplateEngine templateEngine;

    public ReceiptPdfService() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setCacheable(false);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(resolver);
    }

    public byte[] buildReceiptPdf(SandReceiptData d,
                                  byte[] qrPngOrNull,
                                  String qrUrlOrNull) throws Exception {
        byte[] logoBytes = null;
        ClassPathResource logo = new ClassPathResource("static/images/ap_logo.png");
        if (logo.exists()) {
            try (InputStream is = logo.getInputStream()) {
                logoBytes = is.readAllBytes();
            }
        }

        byte[] qrBytes = (qrPngOrNull != null && qrPngOrNull.length > 0)
                ? qrPngOrNull
                : ((qrUrlOrNull != null && !qrUrlOrNull.isBlank()) ? generateQrPng(qrUrlOrNull, 200, 200) : null);

        Context ctx = new Context();
        ctx.setVariable("orderId", d.orderId);
        ctx.setVariable("tripNo", d.tripNo);
        ctx.setVariable("customerName", d.customerName);
        ctx.setVariable("customerMobile", d.customerMobile);
        ctx.setVariable("sandQuantity", d.sandQuantity);
        ctx.setVariable("supplyPoint", d.supplyPoint);
        ctx.setVariable("dispatchDate", d.dispatchDateTime != null
                ? d.dispatchDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a"))
                : "");
        ctx.setVariable("driverName", d.driverName);
        ctx.setVariable("driverMobile", d.driverMobile);
        ctx.setVariable("vehicleNo", d.vehicleNo);
        ctx.setVariable("address", d.address);
        ctx.setVariable("footerLine", d.footerLine);
        if (logoBytes != null) {
            ctx.setVariable("logoBase64", Base64.getEncoder().encodeToString(logoBytes));
        }
        if (qrBytes != null) {
            ctx.setVariable("qrBase64", Base64.getEncoder().encodeToString(qrBytes));
        }

        String html = templateEngine.process("receipt", ctx);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, "");
            // Force the PDF page size to match 80mm receipt width
            builder.useDefaultPageSize(80, 200, PdfRendererBuilder.PageSizeUnits.MM);
            builder.toStream(out);
            builder.run();
            return out.toByteArray();
        }
    }

    /** Generate QR PNG bytes from URL/text (ZXing). */
    private byte[] generateQrPng(String content, int width, int height) throws Exception {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.MARGIN, 1); // quiet zone
        BitMatrix matrix = new MultiFormatWriter()
                .encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage img = MatrixToImageWriter.toBufferedImage(matrix);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(img, "png", baos);
            return baos.toByteArray();
        }
    }
}

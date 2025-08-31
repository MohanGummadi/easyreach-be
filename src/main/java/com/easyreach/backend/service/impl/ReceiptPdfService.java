package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.SandReceiptData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.Map;

/**
 * 72 mm thermal receipt PDF (AP Sand).
 * - Header: logo (left) + two centered lines on right:
 *      AP SAND
 *      MANAGEMENT SYSTEM
 * - Two copies (Consumer / Driver)
 * - Fixed mm-based table; labels use NBSPs to prevent wrapping
 * - Labels left-aligned; values right-aligned (values may wrap)
 * - QR: PNG or generated from URL (scaled to ~75%)
 */
public class ReceiptPdfService {

    // ---- Layout constants ----
    private static final float MM_TO_PT = 72f / 25.4f; // 1 mm ≈ 2.83465 pt
    private static final float PAGE_WIDTH_MM  = 72f;   // printer's printable width
    private static final float PAGE_HEIGHT_MM = 500f;  // tall; printer cuts after content
    private static final float MARGIN_PT = 10f;        // all sides

    // Header sizing
    private static final float HEADER_LOGO_COL_MM = 12f; // logo column
    private static final float HEADER_FONT_SIZE   = 11f;

    // Details table sizing
    private static final float LABEL_COL_MM   = 36f;   // widened so labels fit one line
    private static final float BASE_FONT_SIZE = 8.0f;  // compact & readable

    // QR display size (~75% of 120pt)
    private static final float QR_MAX_WIDTH_PT = 90f;

    public byte[] buildReceiptPdf(SandReceiptData d,
                                  byte[] logoBytes,
                                  byte[] qrPngOrNull,
                                  String qrUrlOrNull) throws Exception {

        float pageWidthPt  = PAGE_WIDTH_MM  * MM_TO_PT;
        float pageHeightPt = PAGE_HEIGHT_MM * MM_TO_PT;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PdfDocument pdf = new PdfDocument(new PdfWriter(out));
             Document doc = new Document(pdf, new PageSize(pageWidthPt, pageHeightPt))) {

            doc.setMargins(MARGIN_PT, MARGIN_PT, MARGIN_PT, MARGIN_PT);

            PdfFont mono = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.COURIER);

            // ---------- Consumer Copy ----------
            doc.add(buildHeader(logoBytes, mono, pageWidthPt));
            doc.add(centered(mono, "Consumer Copy"));
            doc.add(dashedSeparator());
            doc.add(buildDetailsTable(d, mono, pageWidthPt));
            doc.add(dashedSeparator());
            doc.add(new Paragraph(" ").setMarginTop(2));

            // ---------- Driver Copy ----------
            doc.add(buildHeader(logoBytes, mono, pageWidthPt));
            doc.add(centered(mono, "Driver Copy"));
            doc.add(dashedSeparator());
            doc.add(buildDetailsTable(d, mono, pageWidthPt));

            // ---------- QR ----------
            byte[] qrBytes = (qrPngOrNull != null && qrPngOrNull.length > 0)
                    ? qrPngOrNull
                    : ((qrUrlOrNull != null && !qrUrlOrNull.isBlank()) ? generateQrPng(qrUrlOrNull, 200, 200) : null);

            if (qrBytes != null) {
                Image qrImg = new Image(ImageDataFactory.create(qrBytes));
                qrImg.setAutoScale(true);
                qrImg.setMaxWidth(QR_MAX_WIDTH_PT);
                qrImg.setHorizontalAlignment(HorizontalAlignment.CENTER);
                doc.add(qrImg);
            }

            if (d.footerLine != null && !d.footerLine.isBlank()) {
                doc.add(centered(mono, d.footerLine));
            }
        }
        return out.toByteArray();
    }

    // ================== Building blocks ==================

    /** Header: logo left, two centered lines on right; vertically centered with logo. */
    private Table buildHeader(byte[] logoBytes, PdfFont font, float pageWidthPt) {
        float usablePt  = pageWidthPt - (MARGIN_PT * 2);
        float logoColPt = HEADER_LOGO_COL_MM * MM_TO_PT;
        float titleColPt = Math.max(usablePt - logoColPt, 40f);

        Table t = new Table(new float[]{logoColPt, titleColPt})
                .useAllAvailableWidth()
                .setFixedLayout();

        // Left cell: logo centered, vertical middle
        Cell left = new Cell().setBorder(Border.NO_BORDER).setPadding(0)
                .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE);
        if (logoBytes != null && logoBytes.length > 0) {
            Image logo = new Image(ImageDataFactory.create(logoBytes))
                    .setAutoScale(true)
                    .setMaxHeight(40)
                    .setMaxWidth(40)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            left.add(logo);
        }

        // Right cell: two centered lines; vertical middle vs logo
        Paragraph line1 = new Paragraph("AP SAND")
                .setFont(font)
                .setFontSize(HEADER_FONT_SIZE)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMargin(0);

        Paragraph line2 = new Paragraph("MANAGEMENT SYSTEM")
                .setFont(font)
                .setFontSize(HEADER_FONT_SIZE)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMargin(0);

        Cell right = new Cell().setBorder(Border.NO_BORDER).setPadding(0)
                .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE);
        right.add(line1);
        right.add(line2);

        t.addCell(left);
        t.addCell(right);
        return t;
    }

    /** Fixed-width details table: labels (left) single-line via NBSP; values (right) can wrap. */
    private Table buildDetailsTable(SandReceiptData d, PdfFont font, float pageWidthPt) {
        float usablePt   = pageWidthPt - (MARGIN_PT * 2);
        float labelColPt = LABEL_COL_MM * MM_TO_PT;
        float valueColPt = Math.max(usablePt - labelColPt, 60f);

        Table t = new Table(new float[]{labelColPt, valueColPt})
                .useAllAvailableWidth()
                .setFixedLayout();
        t.setFont(font).setFontSize(BASE_FONT_SIZE);

        addKV(t, noWrap("Order Id"),                 d.orderId);
        addKV(t, noWrap("Trip No"),                  d.tripNo);
        addKV(t, noWrap("Coustomer Name"),           d.customerName);
        addKV(t, noWrap("Coustomer Mobile No"),      d.customerMobile);
        addKV(t, noWrap("Sand Quantity:"),           d.sandQuantity);
        addKV(t, noWrap("Sand Supply Point Name"),   d.supplyPoint);

        String dispatch = (d.dispatchDateTime != null)
                ? d.dispatchDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a"))
                : "";
        addKV(t, noWrap("Dispatch Date"),            dispatch);

        addKV(t, noWrap("Driver Name"),              d.driverName);
        addKV(t, noWrap("Driver Mobile No"),         d.driverMobile);
        addKV(t, noWrap("Vehicle No"),               d.vehicleNo);
        addKV(t, noWrap("Adress:"),                  d.address);  // spelling per sample
        return t;
    }

    /** Add a key/value row. Labels are left & single-line (NBSP). Values right-aligned & may wrap. */
    private void addKV(Table t, String k, String v) {
        String lower = k == null ? "" : k.toLowerCase();
        boolean isAddress = lower.startsWith("address") || lower.startsWith("adress"); // accept both spellings

        Paragraph key = new Paragraph(k)
                .setFontSize(BASE_FONT_SIZE)
                .setMargin(0)
                .setPadding(0)
                .setTextAlignment(TextAlignment.LEFT);

        Paragraph val = new Paragraph(v == null ? "" : v)
                .setFontSize(BASE_FONT_SIZE)
                .setMargin(0)
                .setPadding(0)
                .setTextAlignment(isAddress ? TextAlignment.LEFT : TextAlignment.RIGHT); // 👈 difference

        t.addCell(new Cell().add(key)
                .setBorder(Border.NO_BORDER)
                .setPadding(0)
                .setTextAlignment(TextAlignment.LEFT));

        t.addCell(new Cell().add(val)
                .setBorder(Border.NO_BORDER)
                .setPadding(0)
                .setTextAlignment(isAddress ? TextAlignment.LEFT : TextAlignment.RIGHT));
    }


    /** Short dash line sized for narrow paper. */
    private Paragraph dashedSeparator() {
        return new Paragraph("-------------------------------")
                .setFontSize(8)
                .setFontColor(ColorConstants.BLACK)
                .setMarginTop(4)
                .setMarginBottom(4)
                .setMarginLeft(0)
                .setMarginRight(0);
    }

    /** Centered helper paragraph. */
    private Paragraph centered(PdfFont font, String text) {
        return new Paragraph(text)
                .setFont(font)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(2)
                .setMarginBottom(2);
    }

    /** Replace spaces with NBSP to prevent wrapping in labels. */
    private String noWrap(String text) {
        return text == null ? "" : text.replace(" ", "\u00A0");
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

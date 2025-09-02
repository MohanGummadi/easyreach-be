package com.easyreach.backend.controller;

import com.easyreach.backend.dto.SandReceiptData;
import com.easyreach.backend.dto.receipt.ReceiptDto;
import com.easyreach.backend.service.ReceiptService;
import com.easyreach.backend.service.impl.ReceiptPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class ReceiptFormController {

    private final ReceiptService receiptService;
    private final ReceiptPdfService receiptPdfService;

    @GetMapping("/receipts/new")
    public String newReceiptForm(Model model) {
        model.addAttribute("receipt", new ReceiptDto());
        return "receipts/receipt_form";
    }

    @PostMapping(value = "/receipts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> createReceipt(@ModelAttribute ReceiptDto dto,
                                                @RequestParam(value = "qrPng", required = false) MultipartFile qrPng,
                                                @RequestParam(value = "qrUrl", required = false) String qrUrl) throws Exception {

        dto.setQrUrl(qrUrl);
        receiptService.create(dto);

        SandReceiptData data = new SandReceiptData();
        data.orderId = dto.getOrderId();
        data.tripNo = dto.getTripNo();
        data.customerName = dto.getCustomerName();
        data.customerMobile = dto.getCustomerMobile();
        data.sandQuantity = dto.getSandQuantity();
        data.supplyPoint = dto.getSupplyPoint();
        data.dispatchDateTime = dto.getDispatchDateTime();
        data.driverName = dto.getDriverName();
        data.driverMobile = dto.getDriverMobile();
        data.vehicleNo = dto.getVehicleNo();
        data.address = dto.getAddress();
        data.footerLine = dto.getFooterLine();

        byte[] pdf = receiptPdfService.buildReceiptPdf(
                data,
                (qrPng != null && !qrPng.isEmpty()) ? qrPng.getBytes() : null,
                dto.getQrUrl()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("receipt.pdf").build());
        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}


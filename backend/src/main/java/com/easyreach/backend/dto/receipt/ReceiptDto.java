package com.easyreach.backend.dto.receipt;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDto {
    private Long id;
    private String orderId;
    private String tripNo;
    private String customerName;
    private String customerMobile;
    private String sandQuantity;
    private String supplyPoint;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dispatchDateTime;
    private String driverName;
    private String driverMobile;
    private String vehicleNo;
    private String address;
    private String footerLine;
      private String createdBy;
      private OffsetDateTime createdAt;
      private OffsetDateTime updatedAt;
  }


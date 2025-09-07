package com.easyreach.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "trip_no")
    private String tripNo;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_mobile")
    private String customerMobile;

    @Column(name = "sand_quantity")
    private String sandQuantity;

    @Column(name = "dispatch_date_time")
    private LocalDateTime dispatchDateTime;

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "driver_mobile")
    private String driverMobile;

    @Column(name = "vehicle_no")
    private String vehicleNo;

    @Column(name = "address")
    private String address;

    @Column(name = "footer_line")
    private String footerLine;

      @Column(name = "created_by")
      private String createdBy;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    @PreUpdate
    private void uppercaseOrderId() {
        if (orderId != null) {
            orderId = orderId.toUpperCase();
        }
    }
}


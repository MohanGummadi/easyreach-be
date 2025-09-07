package com.easyreach.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "address")
    private String address;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_mobile")
    private String customerMobile;

    @Column(name = "sand_quantity")
    private String sandQuantity;

    @Column(name = "supply_point")
    private String supplyPoint;

    @Column(name = "trip_no")
    private Integer tripNo;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = updatedAt = OffsetDateTime.now();
        uppercaseOrderId();
        if (tripNo == null) {
            tripNo = 0;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
        uppercaseOrderId();
    }

    private void uppercaseOrderId() {
        if (orderId != null) {
            orderId = orderId.toUpperCase();
        }
    }
}

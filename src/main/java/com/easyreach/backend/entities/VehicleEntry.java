package com.easyreach.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vehicle_entries")
public class VehicleEntry {

    @Id
    private String entryId;

    @NotNull
    private String companyId;

    private String vehicleNumber;

    @Column(name = "refferedBy")
    private String referredBy;

    private String paytype;

    private Double amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean deleted = false;
}

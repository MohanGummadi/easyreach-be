package com.easyreach.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "companies")
public class Company {

    @Id
    private String uuid;

    @Column(unique = true)
    private String companyId;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String companyContactNo;

    private String companyCoordinates;

    @Column(nullable = false)
    private String companyLocation;

    @Column(nullable = false)
    private LocalDate companyRegistrationDate;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String ownerMobileNo;

    @Column(nullable = false)
    private String ownerEmailAddress;

    @Column(nullable = false)
    private LocalDate ownerDOB;

    @Column(columnDefinition = "INTEGER DEFAULT 1")
    private Integer isActive;

    @Column(columnDefinition = "INTEGER DEFAULT 0")
    private Integer isSynced;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;
}

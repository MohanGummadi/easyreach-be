package com.easyreach.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.math.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company {
  @Id
  @EqualsAndHashCode.Include
  @Column(name = "uuid", length = 15, nullable = false)
  private String uuid;
  @Column(name = "company_code", nullable = false)
  private String companyCode;
  @Column(name = "company_name", nullable = false)
  private String companyName;
  @Column(name = "company_contact_no", nullable = false)
  private String companyContactNo;
  @Column(name = "company_coordinates")
  private String companyCoordinates;
  @Column(name = "company_location", nullable = false)
  private String companyLocation;
  @Column(name = "company_registration_date", nullable = false)
  private LocalDate companyRegistrationDate;
  @Column(name = "owner_name", nullable = false)
  private String ownerName;
  @Column(name = "owner_mobile_no", nullable = false)
  private String ownerMobileNo;
  @Column(name = "owner_email_address", nullable = false)
  private String ownerEmailAddress;
  @Column(name = "owner_dob", nullable = false)
  private LocalDate ownerDob;
  @Column(name = "is_active", nullable = false)
  private Boolean isActive;
  @Column(name = "is_synced", nullable = false)
  private Boolean isSynced;
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;
  @Column(name = "updated_by")
  private String updatedBy;
  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  @Column(name = "deleted", nullable = false)
  private boolean deleted;

  @Column(name = "deleted_at")
  private OffsetDateTime deletedAt;

  @Version
  @Column(name = "change_id", nullable = false)
  private Long changeId;
}

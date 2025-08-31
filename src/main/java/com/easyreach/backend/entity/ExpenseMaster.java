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
@Table(name = "expense_master")
public class ExpenseMaster {
  @Id
  @EqualsAndHashCode.Include
  @Column(name = "id", length = 20, nullable = false)
  private String id;
  @Column(name = "expense_name", nullable = false)
  private String expenseName;
  @Column(name = "company_uuid", nullable = false)
  private String companyUuid;
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

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "change_id")
  private Long changeId;
}

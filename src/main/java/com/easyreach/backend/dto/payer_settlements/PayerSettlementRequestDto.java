package com.easyreach.backend.dto.payer_settlements;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayerSettlementRequestDto {
  @NotBlank
  private String settlementId;
  @NotBlank
  private String payerId;
  @NotNull
  private BigDecimal amount;
  @NotNull
  private OffsetDateTime date;
  @NotBlank
  private String companyUuid;
  @NotNull
  private Boolean isSynced;

  private String createdBy;
  @NotNull
  private OffsetDateTime createdAt;

  private String updatedBy;
  @NotNull
  private OffsetDateTime updatedAt;

  @NotNull
  private Boolean deleted;

  private OffsetDateTime deletedAt;

  private Long changeId;
}

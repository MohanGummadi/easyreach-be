package com.easyreach.backend.dto.payer_settlements;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayerSettlementResponseDto {
  private String settlementId;
  private String payerId;
  private BigDecimal amount;
  private OffsetDateTime date;
  private String companyUuid;
  private Boolean isSynced;
  private String createdBy;
  private OffsetDateTime createdAt;
  private String updatedBy;
  private OffsetDateTime updatedAt;
}

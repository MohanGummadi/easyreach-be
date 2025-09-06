package com.easyreach.backend.dto.payer_settlements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayerSettlementWithNameDto {
    private String settlementId;
    private String payerId;
    private String payerName;
    private BigDecimal amount;
    private OffsetDateTime date;
}

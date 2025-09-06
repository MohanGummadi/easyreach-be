package com.easyreach.backend.dto.ledger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayerLedgerSummaryDto {
    private String payerId;
    private String payerName;
    private BigDecimal pendingAmt;
}

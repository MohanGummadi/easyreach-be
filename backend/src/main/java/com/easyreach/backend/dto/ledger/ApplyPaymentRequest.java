package com.easyreach.backend.dto.ledger;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ApplyPaymentRequest {
    private BigDecimal amount;
    private String settlementType;
}

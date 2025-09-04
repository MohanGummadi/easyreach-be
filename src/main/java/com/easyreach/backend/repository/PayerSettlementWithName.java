package com.easyreach.backend.repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/** Projection for PayerSettlement joined with payer name */
public interface PayerSettlementWithName {
    String getSettlementId();
    String getPayerId();
    String getPayerName();
    BigDecimal getAmount();
    OffsetDateTime getDate();
}

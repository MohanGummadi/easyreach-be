package com.easyreach.backend.repository;

import java.math.BigDecimal;

/**
 * Projection interface for summarising pending amounts per payer.
 */
public interface PayerLedgerSummary {
    String getPayerId();
    String getPayerName();
    BigDecimal getPendingAmt();
}

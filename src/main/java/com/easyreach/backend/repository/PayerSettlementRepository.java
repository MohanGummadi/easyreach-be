package com.easyreach.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easyreach.backend.entity.PayerSettlement;

public interface PayerSettlementRepository extends JpaRepository<PayerSettlement, String> {
}

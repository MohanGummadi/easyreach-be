package com.easyreach.backend.repository;

import com.easyreach.backend.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Optional<Receipt> findFirstByOrderIdIgnoreCase(String orderId);
}


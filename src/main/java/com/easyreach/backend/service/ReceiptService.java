package com.easyreach.backend.service;

import com.easyreach.backend.dto.receipt.ReceiptDto;
import com.easyreach.backend.entity.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReceiptService {
    Receipt create(ReceiptDto dto);
    Page<Receipt> list(Pageable pageable);
    Optional<ReceiptDto> findByOrderId(String orderId);
}


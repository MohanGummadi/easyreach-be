package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.receipt.ReceiptDto;
import com.easyreach.backend.entity.Receipt;
import com.easyreach.backend.repository.ReceiptRepository;
import com.easyreach.backend.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository repository;

    @Override
    public Receipt create(ReceiptDto dto) {
        log.debug("Creating receipt for order {}", dto.getOrderId());
        Receipt receipt = Receipt.builder()
                .orderId(dto.getOrderId() != null ? dto.getOrderId().toUpperCase() : null)
                .tripNo(dto.getTripNo())
                .customerName(dto.getCustomerName())
                .customerMobile(dto.getCustomerMobile())
                .sandQuantity(dto.getSandQuantity())
                .supplyPoint(dto.getSupplyPoint())
                .dispatchDateTime(dto.getDispatchDateTime())
                .driverName(dto.getDriverName())
                .driverMobile(dto.getDriverMobile())
                .vehicleNo(dto.getVehicleNo())
                .address(dto.getAddress())
                .footerLine(dto.getFooterLine())
                .qrUrl(dto.getQrUrl())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
        return repository.save(receipt);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Receipt> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ReceiptDto findByOrderId(String orderId) {
        return repository.findFirstByOrderIdIgnoreCase(orderId)
                .map(r -> ReceiptDto.builder()
                        .id(r.getId())
                        .orderId(r.getOrderId())
                        .tripNo(r.getTripNo())
                        .customerName(r.getCustomerName())
                        .customerMobile(r.getCustomerMobile())
                        .sandQuantity(r.getSandQuantity())
                        .supplyPoint(r.getSupplyPoint())
                        .dispatchDateTime(r.getDispatchDateTime())
                        .driverName(null)
                        .driverMobile(null)
                        .vehicleNo(null)
                        .address(r.getAddress())
                        .footerLine(r.getFooterLine())
                        .qrUrl(r.getQrUrl())
                        .createdAt(r.getCreatedAt())
                        .updatedAt(r.getUpdatedAt())
                        .build())
                .orElse(null);
    }
}


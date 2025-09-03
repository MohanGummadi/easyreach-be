package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.receipt.ReceiptDto;
import com.easyreach.backend.entity.Receipt;
import com.easyreach.backend.repository.ReceiptRepository;
import com.easyreach.backend.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository repository;

    private static final String SUPPLY_POINT = "Khandyam";
    private static final String FOOTER_LINE = "18.4060366,83.9543993 Thank you";

    @Override
    public Receipt create(ReceiptDto dto) {
        log.debug("Creating receipt for order {}", dto.getOrderId());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Receipt receipt = Receipt.builder()
                .orderId(dto.getOrderId() != null ? dto.getOrderId().toUpperCase() : null)
                .tripNo(dto.getTripNo())
                .customerName(dto.getCustomerName())
                .customerMobile(dto.getCustomerMobile())
                .sandQuantity(dto.getSandQuantity())
                .supplyPoint(SUPPLY_POINT)
                .dispatchDateTime(dto.getDispatchDateTime())
                .driverName(dto.getDriverName())
                .driverMobile(dto.getDriverMobile())
                .vehicleNo(dto.getVehicleNo())
                .address(dto.getAddress())
                .footerLine(FOOTER_LINE)
                .qrUrl(dto.getQrUrl())
                .createdBy(username)
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
                        .createdBy(r.getCreatedBy())
                        .createdAt(r.getCreatedAt())
                        .updatedAt(r.getUpdatedAt())
                        .build())
                .orElse(null);
    }
}


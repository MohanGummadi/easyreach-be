package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.entity.VehicleEntry;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class VehicleEntryMapperTest {

    private final VehicleEntryMapper mapper = Mappers.getMapper(VehicleEntryMapper.class);

    @Test
    void toEntity_and_toDto() {
        String entryId = "ve1";
        String companyUuid = "comp1";
        String payerId = "p1";
        String vehicleNumber = "AB123";
        String vehicleType = "Truck";
        String fromAddress = "Source";
        String toAddress = "Destination";
        String driverName = "Driver";
        String driverContactNo = "1234567890";
        BigDecimal commission = new BigDecimal("50.00");
        BigDecimal beta = new BigDecimal("20.00");
        String referredBy = "Referrer";
        BigDecimal amount = new BigDecimal("200.00");
        String paytype = "Cash";
        LocalDate entryDate = LocalDate.parse("2023-01-01");
        OffsetDateTime entryTime = OffsetDateTime.parse("2023-01-01T08:00:00Z");
        OffsetDateTime exitTime = OffsetDateTime.parse("2023-01-01T10:00:00Z");
        String notes = "note";
        String paymentReceivedBy = "Receiver";
        BigDecimal paidAmount = new BigDecimal("150.00");
        BigDecimal pendingAmt = new BigDecimal("50.00");
        Boolean isSettled = false;
        String settlementType = "Partial";
        OffsetDateTime settlementDate = OffsetDateTime.parse("2023-01-02T10:00:00Z");
        Boolean isSynced = true;
        String createdBy = "creator";
        OffsetDateTime createdAt = OffsetDateTime.parse("2023-01-01T00:00:00Z");
        String updatedBy = "upd";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-01-03T00:00:00Z");
        Boolean deleted = true;
        OffsetDateTime deletedAt = OffsetDateTime.parse("2023-01-04T00:00:00Z");
        Long changeId = 30L;

        VehicleEntryRequestDto dto = new VehicleEntryRequestDto();
        dto.setEntryId(entryId);
        dto.setCompanyUuid(companyUuid);
        dto.setPayerId(payerId);
        dto.setVehicleNumber(vehicleNumber);
        dto.setVehicleType(vehicleType);
        dto.setFromAddress(fromAddress);
        dto.setToAddress(toAddress);
        dto.setDriverName(driverName);
        dto.setDriverContactNo(driverContactNo);
        dto.setCommission(commission);
        dto.setBeta(beta);
        dto.setReferredBy(referredBy);
        dto.setAmount(amount);
        dto.setPaytype(paytype);
        dto.setEntryDate(entryDate);
        dto.setEntryTime(entryTime);
        dto.setExitTime(exitTime);
        dto.setNotes(notes);
        dto.setPaymentReceivedBy(paymentReceivedBy);
        dto.setPaidAmount(paidAmount);
        dto.setPendingAmt(pendingAmt);
        dto.setIsSettled(isSettled);
        dto.setSettlementType(settlementType);
        dto.setSettlementDate(settlementDate);
        dto.setIsSynced(isSynced);
        dto.setCreatedBy(createdBy);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedBy(updatedBy);
        dto.setUpdatedAt(updatedAt);
        dto.setDeleted(deleted);
        dto.setDeletedAt(deletedAt);
        dto.setChangeId(changeId);

        VehicleEntry expectedEntity = VehicleEntry.builder()
                .entryId(entryId)
                .companyUuid(companyUuid)
                .payerId(payerId)
                .vehicleNumber(vehicleNumber)
                .vehicleType(vehicleType)
                .fromAddress(fromAddress)
                .toAddress(toAddress)
                .driverName(driverName)
                .driverContactNo(driverContactNo)
                .commission(commission)
                .beta(beta)
                .referredBy(referredBy)
                .amount(amount)
                .paytype(paytype)
                .entryDate(entryDate)
                .entryTime(entryTime)
                .exitTime(exitTime)
                .notes(notes)
                .paymentReceivedBy(paymentReceivedBy)
                .paidAmount(paidAmount)
                .pendingAmt(pendingAmt)
                .isSettled(isSettled)
                .settlementType(settlementType)
                .settlementDate(settlementDate)
                .isSynced(isSynced)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .deleted(deleted)
                .deletedAt(deletedAt)
                .changeId(changeId)
                .build();

        VehicleEntry entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        VehicleEntryResponseDto expectedDto = new VehicleEntryResponseDto();
        expectedDto.setEntryId(entryId);
        expectedDto.setCompanyUuid(companyUuid);
        expectedDto.setPayerId(payerId);
        expectedDto.setVehicleNumber(vehicleNumber);
        expectedDto.setVehicleType(vehicleType);
        expectedDto.setFromAddress(fromAddress);
        expectedDto.setToAddress(toAddress);
        expectedDto.setDriverName(driverName);
        expectedDto.setDriverContactNo(driverContactNo);
        expectedDto.setCommission(commission);
        expectedDto.setBeta(beta);
        expectedDto.setReferredBy(referredBy);
        expectedDto.setAmount(amount);
        expectedDto.setPaytype(paytype);
        expectedDto.setEntryDate(entryDate);
        expectedDto.setEntryTime(entryTime);
        expectedDto.setExitTime(exitTime);
        expectedDto.setNotes(notes);
        expectedDto.setPaymentReceivedBy(paymentReceivedBy);
        expectedDto.setPaidAmount(paidAmount);
        expectedDto.setPendingAmt(pendingAmt);
        expectedDto.setIsSettled(isSettled);
        expectedDto.setSettlementType(settlementType);
        expectedDto.setSettlementDate(settlementDate);
        expectedDto.setIsSynced(isSynced);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedBy(updatedBy);
        expectedDto.setUpdatedAt(updatedAt);
        expectedDto.setDeleted(deleted);
        expectedDto.setDeletedAt(deletedAt);
        expectedDto.setChangeId(changeId);

        VehicleEntryResponseDto actualDto = mapper.toDto(entity);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}

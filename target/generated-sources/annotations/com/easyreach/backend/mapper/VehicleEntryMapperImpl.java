package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.entity.VehicleEntry;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:20+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class VehicleEntryMapperImpl implements VehicleEntryMapper {

    @Override
    public VehicleEntry toEntity(VehicleEntryRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        VehicleEntry.VehicleEntryBuilder vehicleEntry = VehicleEntry.builder();

        vehicleEntry.entryId( dto.getEntryId() );
        vehicleEntry.companyUuid( dto.getCompanyUuid() );
        vehicleEntry.payerId( dto.getPayerId() );
        vehicleEntry.vehicleNumber( dto.getVehicleNumber() );
        vehicleEntry.vehicleType( dto.getVehicleType() );
        vehicleEntry.fromAddress( dto.getFromAddress() );
        vehicleEntry.toAddress( dto.getToAddress() );
        vehicleEntry.driverName( dto.getDriverName() );
        vehicleEntry.driverContactNo( dto.getDriverContactNo() );
        vehicleEntry.commission( dto.getCommission() );
        vehicleEntry.beta( dto.getBeta() );
        vehicleEntry.referredBy( dto.getReferredBy() );
        vehicleEntry.amount( dto.getAmount() );
        vehicleEntry.paytype( dto.getPaytype() );
        vehicleEntry.entryDate( dto.getEntryDate() );
        vehicleEntry.entryTime( dto.getEntryTime() );
        vehicleEntry.exitTime( dto.getExitTime() );
        vehicleEntry.notes( dto.getNotes() );
        vehicleEntry.paymentReceivedBy( dto.getPaymentReceivedBy() );
        vehicleEntry.paidAmount( dto.getPaidAmount() );
        vehicleEntry.pendingAmt( dto.getPendingAmt() );
        vehicleEntry.isSettled( dto.getIsSettled() );
        vehicleEntry.settlementType( dto.getSettlementType() );
        vehicleEntry.settlementDate( dto.getSettlementDate() );
        vehicleEntry.isSynced( dto.getIsSynced() );
        vehicleEntry.createdBy( dto.getCreatedBy() );
        vehicleEntry.createdAt( dto.getCreatedAt() );
        vehicleEntry.updatedBy( dto.getUpdatedBy() );
        vehicleEntry.updatedAt( dto.getUpdatedAt() );

        return vehicleEntry.build();
    }

    @Override
    public VehicleEntryResponseDto toDto(VehicleEntry entity) {
        if ( entity == null ) {
            return null;
        }

        VehicleEntryResponseDto vehicleEntryResponseDto = new VehicleEntryResponseDto();

        vehicleEntryResponseDto.setEntryId( entity.getEntryId() );
        vehicleEntryResponseDto.setCompanyUuid( entity.getCompanyUuid() );
        vehicleEntryResponseDto.setPayerId( entity.getPayerId() );
        vehicleEntryResponseDto.setVehicleNumber( entity.getVehicleNumber() );
        vehicleEntryResponseDto.setVehicleType( entity.getVehicleType() );
        vehicleEntryResponseDto.setFromAddress( entity.getFromAddress() );
        vehicleEntryResponseDto.setToAddress( entity.getToAddress() );
        vehicleEntryResponseDto.setDriverName( entity.getDriverName() );
        vehicleEntryResponseDto.setDriverContactNo( entity.getDriverContactNo() );
        vehicleEntryResponseDto.setCommission( entity.getCommission() );
        vehicleEntryResponseDto.setBeta( entity.getBeta() );
        vehicleEntryResponseDto.setReferredBy( entity.getReferredBy() );
        vehicleEntryResponseDto.setAmount( entity.getAmount() );
        vehicleEntryResponseDto.setPaytype( entity.getPaytype() );
        vehicleEntryResponseDto.setEntryDate( entity.getEntryDate() );
        vehicleEntryResponseDto.setEntryTime( entity.getEntryTime() );
        vehicleEntryResponseDto.setExitTime( entity.getExitTime() );
        vehicleEntryResponseDto.setNotes( entity.getNotes() );
        vehicleEntryResponseDto.setPaymentReceivedBy( entity.getPaymentReceivedBy() );
        vehicleEntryResponseDto.setPaidAmount( entity.getPaidAmount() );
        vehicleEntryResponseDto.setPendingAmt( entity.getPendingAmt() );
        vehicleEntryResponseDto.setIsSettled( entity.getIsSettled() );
        vehicleEntryResponseDto.setSettlementType( entity.getSettlementType() );
        vehicleEntryResponseDto.setSettlementDate( entity.getSettlementDate() );
        vehicleEntryResponseDto.setIsSynced( entity.getIsSynced() );
        vehicleEntryResponseDto.setCreatedBy( entity.getCreatedBy() );
        vehicleEntryResponseDto.setCreatedAt( entity.getCreatedAt() );
        vehicleEntryResponseDto.setUpdatedBy( entity.getUpdatedBy() );
        vehicleEntryResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return vehicleEntryResponseDto;
    }

    @Override
    public void update(VehicleEntry entity, VehicleEntryRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getEntryId() != null ) {
            entity.setEntryId( dto.getEntryId() );
        }
        if ( dto.getCompanyUuid() != null ) {
            entity.setCompanyUuid( dto.getCompanyUuid() );
        }
        if ( dto.getPayerId() != null ) {
            entity.setPayerId( dto.getPayerId() );
        }
        if ( dto.getVehicleNumber() != null ) {
            entity.setVehicleNumber( dto.getVehicleNumber() );
        }
        if ( dto.getVehicleType() != null ) {
            entity.setVehicleType( dto.getVehicleType() );
        }
        if ( dto.getFromAddress() != null ) {
            entity.setFromAddress( dto.getFromAddress() );
        }
        if ( dto.getToAddress() != null ) {
            entity.setToAddress( dto.getToAddress() );
        }
        if ( dto.getDriverName() != null ) {
            entity.setDriverName( dto.getDriverName() );
        }
        if ( dto.getDriverContactNo() != null ) {
            entity.setDriverContactNo( dto.getDriverContactNo() );
        }
        if ( dto.getCommission() != null ) {
            entity.setCommission( dto.getCommission() );
        }
        if ( dto.getBeta() != null ) {
            entity.setBeta( dto.getBeta() );
        }
        if ( dto.getReferredBy() != null ) {
            entity.setReferredBy( dto.getReferredBy() );
        }
        if ( dto.getAmount() != null ) {
            entity.setAmount( dto.getAmount() );
        }
        if ( dto.getPaytype() != null ) {
            entity.setPaytype( dto.getPaytype() );
        }
        if ( dto.getEntryDate() != null ) {
            entity.setEntryDate( dto.getEntryDate() );
        }
        if ( dto.getEntryTime() != null ) {
            entity.setEntryTime( dto.getEntryTime() );
        }
        if ( dto.getExitTime() != null ) {
            entity.setExitTime( dto.getExitTime() );
        }
        if ( dto.getNotes() != null ) {
            entity.setNotes( dto.getNotes() );
        }
        if ( dto.getPaymentReceivedBy() != null ) {
            entity.setPaymentReceivedBy( dto.getPaymentReceivedBy() );
        }
        if ( dto.getPaidAmount() != null ) {
            entity.setPaidAmount( dto.getPaidAmount() );
        }
        if ( dto.getPendingAmt() != null ) {
            entity.setPendingAmt( dto.getPendingAmt() );
        }
        if ( dto.getIsSettled() != null ) {
            entity.setIsSettled( dto.getIsSettled() );
        }
        if ( dto.getSettlementType() != null ) {
            entity.setSettlementType( dto.getSettlementType() );
        }
        if ( dto.getSettlementDate() != null ) {
            entity.setSettlementDate( dto.getSettlementDate() );
        }
        if ( dto.getIsSynced() != null ) {
            entity.setIsSynced( dto.getIsSynced() );
        }
        if ( dto.getCreatedBy() != null ) {
            entity.setCreatedBy( dto.getCreatedBy() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getUpdatedBy() != null ) {
            entity.setUpdatedBy( dto.getUpdatedBy() );
        }
        if ( dto.getUpdatedAt() != null ) {
            entity.setUpdatedAt( dto.getUpdatedAt() );
        }
    }
}

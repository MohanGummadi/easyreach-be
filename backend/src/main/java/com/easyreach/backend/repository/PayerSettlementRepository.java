package com.easyreach.backend.repository;

import com.easyreach.backend.entity.PayerSettlement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PayerSettlementRepository extends JpaRepository<PayerSettlement, String> {
    List<PayerSettlement> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<PayerSettlement> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    Optional<PayerSettlement> findBySettlementIdAndCompanyUuidAndDeletedIsFalse(String settlementId, String companyUuid);

    Page<PayerSettlement> findByCompanyUuidAndDeletedIsFalse(String companyUuid, Pageable pageable);

    List<PayerSettlement> findBySettlementIdInAndCompanyUuid(Collection<String> settlementIds, String companyUuid);

    List<PayerSettlement> findByPayerIdAndCompanyUuidAndDeletedIsFalse(String payerId, String companyUuid);

    List<PayerSettlement> findByCompanyUuidAndDeletedIsFalse(String companyUuid);

    @Query("select ps.settlementId as settlementId, ps.payerId as payerId, p.payerName as payerName, ps.amount as amount, ps.date as date " +
            "from PayerSettlement ps join Payer p on ps.payerId = p.payerId and p.deleted = false " +
            "where ps.companyUuid = :companyUuid and ps.deleted = false")
    List<PayerSettlementWithName> findByCompanyUuidWithPayerName(@Param("companyUuid") String companyUuid);

    @Query("select ps.settlementId as settlementId, ps.payerId as payerId, p.payerName as payerName, ps.amount as amount, ps.date as date " +
            "from PayerSettlement ps join Payer p on ps.payerId = p.payerId and p.deleted = false " +
            "where ps.payerId = :payerId and ps.companyUuid = :companyUuid and ps.deleted = false")
    List<PayerSettlementWithName> findByPayerIdWithName(@Param("payerId") String payerId, @Param("companyUuid") String companyUuid);
}

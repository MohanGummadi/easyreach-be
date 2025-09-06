package com.easyreach.backend.repository;

import com.easyreach.backend.entity.VehicleEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface VehicleEntryRepository extends JpaRepository<VehicleEntry, String> {
    List<VehicleEntry> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<VehicleEntry> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    Optional<VehicleEntry> findByEntryIdAndCompanyUuidAndDeletedIsFalse(String entryId, String companyUuid);

    Page<VehicleEntry> findByCompanyUuidAndDeletedIsFalse(String companyUuid, Pageable pageable);

    List<VehicleEntry> findByEntryIdInAndCompanyUuid(Collection<String> entryIds, String companyUuid);

    Page<VehicleEntry> findByCompanyUuidAndPayerIdAndDeletedIsFalseOrderByCreatedAtDesc(String companyUuid, String payerId, Pageable pageable);

    Page<VehicleEntry> findByCompanyUuidAndDeletedIsFalseOrderByCreatedAtDesc(String companyUuid, Pageable pageable);

    List<VehicleEntry> findByCompanyUuidAndPayerIdAndDeletedIsFalseAndIsSettledFalseOrderByEntryTimeAsc(String companyUuid, String payerId);

    @Query("select v.payerId as payerId, p.payerName as payerName, sum(v.pendingAmt) as pendingAmt " +
            "from VehicleEntry v join Payer p on v.payerId = p.payerId and p.deleted = false " +
            "where v.companyUuid = :companyUuid and v.deleted = false " +
            "group by v.payerId, p.payerName")
    java.util.List<PayerLedgerSummary> summarizePendingByPayer(@Param("companyUuid") String companyUuid);
}

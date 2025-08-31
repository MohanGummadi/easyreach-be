package com.easyreach.backend.repository;

import com.easyreach.backend.entity.PayerSettlement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface PayerSettlementRepository extends JpaRepository<PayerSettlement, String> {
    List<PayerSettlement> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<PayerSettlement> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    Optional<PayerSettlement> findBySettlementIdAndCompanyUuidAndDeletedIsFalse(String settlementId, String companyUuid);

    Page<PayerSettlement> findByCompanyUuidAndDeletedIsFalse(String companyUuid, Pageable pageable);
}

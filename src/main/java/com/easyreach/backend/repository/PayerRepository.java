package com.easyreach.backend.repository;

import com.easyreach.backend.entity.Payer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface PayerRepository extends JpaRepository<Payer, String> {
    Page<Payer> findByCompanyUuidAndDeletedAtIsNull(String companyUuid, Pageable pageable);
    Page<Payer> findByCompanyUuidAndPayerNameContainingIgnoreCaseAndDeletedAtIsNull(String companyUuid, String q, Pageable pageable);

    List<Payer> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<Payer> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    Optional<Payer> findByPayerIdAndDeletedIsFalse(String payerId);

    Page<Payer> findByDeletedIsFalse(Pageable pageable);
}

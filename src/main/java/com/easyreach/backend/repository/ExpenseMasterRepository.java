package com.easyreach.backend.repository;

import com.easyreach.backend.entity.ExpenseMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ExpenseMasterRepository extends JpaRepository<ExpenseMaster, String> {
    List<ExpenseMaster> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<ExpenseMaster> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    Optional<ExpenseMaster> findByIdAndCompanyUuidAndDeletedIsFalse(String id, String companyUuid);

    Page<ExpenseMaster> findByCompanyUuidAndDeletedIsFalse(String companyUuid, Pageable pageable);
}

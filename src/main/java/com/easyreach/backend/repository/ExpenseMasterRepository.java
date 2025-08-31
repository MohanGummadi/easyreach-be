package com.easyreach.backend.repository;

import com.easyreach.backend.entity.ExpenseMaster;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface ExpenseMasterRepository extends JpaRepository<ExpenseMaster, String> {
    List<ExpenseMaster> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<ExpenseMaster> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);
}

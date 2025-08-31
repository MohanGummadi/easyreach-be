package com.easyreach.backend.repository;

import com.easyreach.backend.entity.DailyExpense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface DailyExpenseRepository extends JpaRepository<DailyExpense, String> {
    List<DailyExpense> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<DailyExpense> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    Optional<DailyExpense> findByExpenseIdAndCompanyUuidAndDeletedIsFalse(String expenseId, String companyUuid);

    Page<DailyExpense> findByCompanyUuidAndDeletedIsFalse(String companyUuid, Pageable pageable);
}

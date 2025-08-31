package com.easyreach.backend.repository;

import com.easyreach.backend.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {
    List<Company> findByUuidAndUpdatedAtGreaterThanEqual(String uuid, OffsetDateTime cursor, Pageable pageable);

    List<Company> findByUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String uuid, OffsetDateTime cursor, Pageable pageable);

    Optional<Company> findByUuidAndDeletedIsFalse(String uuid);

    Page<Company> findByDeletedIsFalse(Pageable pageable);
}

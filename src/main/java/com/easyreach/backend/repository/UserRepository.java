package com.easyreach.backend.repository;

import com.easyreach.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findByEmailIgnoreCaseAndCompanyUuid(String email, String companyUuid);
    Optional<User> findByMobileNo(String mobileNo);

    List<User> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<User> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    Optional<User> findByIdAndCompanyUuidAndDeletedIsFalse(String id, String companyUuid);

    Page<User> findByCompanyUuidAndDeletedIsFalse(String companyUuid, Pageable pageable);
}

package com.easyreach.backend.repository;

import com.easyreach.backend.entity.Payer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayerRepository extends JpaRepository<Payer, String> {
    Page<Payer> findByCompanyUuidAndDeletedAtIsNull(String companyUuid, Pageable pageable);
    Page<Payer> findByCompanyUuidAndPayerNameContainingIgnoreCaseAndDeletedAtIsNull(String companyUuid, String q, Pageable pageable);
}

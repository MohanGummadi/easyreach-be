package com.easyreach.backend.repositories;

import com.easyreach.backend.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Optional<Company> findByCompanyId(String companyId);
}

package com.easyreach.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easyreach.backend.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, String> {
}

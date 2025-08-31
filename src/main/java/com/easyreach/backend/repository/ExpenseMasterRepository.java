package com.easyreach.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easyreach.backend.entity.ExpenseMaster;

public interface ExpenseMasterRepository extends JpaRepository<ExpenseMaster, String> {
}

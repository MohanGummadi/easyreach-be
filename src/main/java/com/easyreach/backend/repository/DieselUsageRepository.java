package com.easyreach.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easyreach.backend.entity.DieselUsage;

public interface DieselUsageRepository extends JpaRepository<DieselUsage, String> {
}

package com.easyreach.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easyreach.backend.entity.EquipmentUsage;

public interface EquipmentUsageRepository extends JpaRepository<EquipmentUsage, String> {
}

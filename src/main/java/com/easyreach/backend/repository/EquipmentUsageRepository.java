package com.easyreach.backend.repository;

import com.easyreach.backend.entity.EquipmentUsage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface EquipmentUsageRepository extends JpaRepository<EquipmentUsage, String> {
    List<EquipmentUsage> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<EquipmentUsage> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);
}

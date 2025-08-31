package com.easyreach.backend.repository;

import com.easyreach.backend.entity.VehicleEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface VehicleEntryRepository extends JpaRepository<VehicleEntry, String> {
    List<VehicleEntry> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<VehicleEntry> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);
}

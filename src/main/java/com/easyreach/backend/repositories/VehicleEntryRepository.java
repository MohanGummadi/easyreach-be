package com.easyreach.backend.repositories;

import com.easyreach.backend.entities.VehicleEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface VehicleEntryRepository extends JpaRepository<VehicleEntry, String> {
    Page<VehicleEntry> findByCompanyId(String companyId, Pageable pageable);
    Page<VehicleEntry> findByCompanyIdAndCreatedAtBetween(
            String companyId,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable);
}

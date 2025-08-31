package com.easyreach.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easyreach.backend.entity.VehicleEntry;

public interface VehicleEntryRepository extends JpaRepository<VehicleEntry, String> {
}

package com.easyreach.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easyreach.backend.entity.VehicleType;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, String> {
}

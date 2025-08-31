package com.easyreach.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easyreach.backend.entity.InternalVehicle;

public interface InternalVehicleRepository extends JpaRepository<InternalVehicle, String> {
}

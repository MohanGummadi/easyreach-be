package com.easyreach.backend.repository;

import com.easyreach.backend.entity.InternalVehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface InternalVehicleRepository extends JpaRepository<InternalVehicle, String> {
    List<InternalVehicle> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<InternalVehicle> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    Optional<InternalVehicle> findByVehicleIdAndCompanyUuidAndDeletedIsFalse(String vehicleId, String companyUuid);

    Page<InternalVehicle> findByCompanyUuidAndDeletedIsFalse(String companyUuid, Pageable pageable);
}

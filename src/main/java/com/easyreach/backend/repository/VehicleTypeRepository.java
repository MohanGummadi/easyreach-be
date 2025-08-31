package com.easyreach.backend.repository;

import com.easyreach.backend.entity.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, String> {
    List<VehicleType> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<VehicleType> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    Optional<VehicleType> findByIdAndDeletedIsFalse(String id);

    Page<VehicleType> findByDeletedIsFalse(Pageable pageable);
}

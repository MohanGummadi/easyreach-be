package com.easyreach.backend.repository;

import com.easyreach.backend.entity.VehicleEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface VehicleEntryRepository extends JpaRepository<VehicleEntry, String> {
    List<VehicleEntry> findByCompanyUuidAndUpdatedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    List<VehicleEntry> findByCompanyUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(String companyUuid, OffsetDateTime cursor, Pageable pageable);

    Optional<VehicleEntry> findByEntryIdAndCompanyUuidAndDeletedIsFalse(String entryId, String companyUuid);

    Page<VehicleEntry> findByCompanyUuidAndDeletedIsFalse(String companyUuid, Pageable pageable);

    List<VehicleEntry> findByEntryIdInAndCompanyUuid(Collection<String> entryIds, String companyUuid);
}

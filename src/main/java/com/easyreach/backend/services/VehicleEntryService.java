package com.easyreach.backend.services;

import com.easyreach.backend.dto.VehicleEntryDto;
import com.easyreach.backend.entities.VehicleEntry;
import com.easyreach.backend.mappers.VehicleEntryMapper;
import com.easyreach.backend.repositories.VehicleEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VehicleEntryService {

    private final VehicleEntryRepository repository;
    private final VehicleEntryMapper mapper;

    @Autowired
    public VehicleEntryService(VehicleEntryRepository repository, VehicleEntryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public VehicleEntryDto create(VehicleEntryDto dto) {
        validate(dto);
        VehicleEntry entity = mapper.toEntity(dto);
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        return mapper.toDto(repository.save(entity));
    }

    public VehicleEntryDto upsert(VehicleEntryDto dto) {
        validate(dto);
        Optional<VehicleEntry> existingOpt = repository.findById(dto.getEntryId());
        if (existingOpt.isPresent()) {
            VehicleEntry existing = existingOpt.get();
            if (!existing.getCompanyId().equals(dto.getCompanyId())) {
                throw new IllegalArgumentException("Company mismatch");
            }
            existing.setVehicleNumber(dto.getVehicleNumber());
            existing.setReferredBy(dto.getReferredBy());
            existing.setPaytype(dto.getPaytype());
            existing.setAmount(dto.getAmount());
            existing.setUpdatedAt(LocalDateTime.now());
            return mapper.toDto(repository.save(existing));
        }
        return create(dto);
    }

    public VehicleEntryDto get(String id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    public Page<VehicleEntryDto> list(String companyId, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        if (start != null && end != null) {
            return repository.findByCompanyIdAndCreatedAtBetween(companyId, start, end, pageable)
                    .map(mapper::toDto);
        }
        return repository.findByCompanyId(companyId, pageable).map(mapper::toDto);
    }

    public VehicleEntryDto update(String id, VehicleEntryDto dto) {
        Optional<VehicleEntry> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }
        VehicleEntry existing = existingOpt.get();
        if (!existing.getCompanyId().equals(dto.getCompanyId())) {
            throw new IllegalArgumentException("Company mismatch");
        }
        existing.setVehicleNumber(dto.getVehicleNumber());
        existing.setReferredBy(dto.getReferredBy());
        existing.setPaytype(dto.getPaytype());
        existing.setAmount(dto.getAmount());
        existing.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repository.save(existing));
    }

    public void delete(String id) {
        repository.findById(id).ifPresent(entity -> {
            entity.setDeleted(true);
            entity.setUpdatedAt(LocalDateTime.now());
            repository.save(entity);
        });
    }

    private void validate(VehicleEntryDto dto) {
        if (dto.getEntryId() == null || dto.getCompanyId() == null) {
            throw new IllegalArgumentException("entryId and companyId are required");
        }
    }
}

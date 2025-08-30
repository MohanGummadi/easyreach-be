package com.easyreach.backend.services;

import com.easyreach.backend.dto.CompanyDto;
import com.easyreach.backend.entities.Company;
import com.easyreach.backend.mappers.CompanyMapper;
import com.easyreach.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    @Autowired
    public CompanyService(CompanyRepository repository, CompanyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CompanyDto create(CompanyDto dto) {
        validate(dto);
        Company entity = mapper.toEntity(dto);
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        return mapper.toDto(repository.save(entity));
    }

    public CompanyDto upsert(CompanyDto dto) {
        validate(dto);
        Optional<Company> existingOpt = repository.findById(dto.getUuid());
        if (existingOpt.isPresent()) {
            Company existing = existingOpt.get();
            updateEntity(existing, dto);
            existing.setUpdatedAt(LocalDateTime.now());
            return mapper.toDto(repository.save(existing));
        }
        return create(dto);
    }

    public CompanyDto get(String id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    public Page<CompanyDto> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public CompanyDto update(String id, CompanyDto dto) {
        Optional<Company> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }
        Company existing = existingOpt.get();
        updateEntity(existing, dto);
        existing.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repository.save(existing));
    }

    public void delete(String id) {
        repository.findById(id).ifPresent(entity -> {
            entity.setIsActive(0);
            entity.setUpdatedAt(LocalDateTime.now());
            repository.save(entity);
        });
    }

    private void updateEntity(Company existing, CompanyDto dto) {
        existing.setCompanyId(dto.getCompanyId());
        existing.setCompanyName(dto.getCompanyName());
        existing.setCompanyContactNo(dto.getCompanyContactNo());
        existing.setCompanyCoordinates(dto.getCompanyCoordinates());
        existing.setCompanyLocation(dto.getCompanyLocation());
        existing.setCompanyRegistrationDate(dto.getCompanyRegistrationDate());
        existing.setOwnerName(dto.getOwnerName());
        existing.setOwnerMobileNo(dto.getOwnerMobileNo());
        existing.setOwnerEmailAddress(dto.getOwnerEmailAddress());
        existing.setOwnerDOB(dto.getOwnerDOB());
        existing.setIsActive(dto.getIsActive());
        existing.setIsSynced(dto.getIsSynced());
        existing.setCreatedBy(dto.getCreatedBy());
        existing.setUpdatedBy(dto.getUpdatedBy());
    }

    private void validate(CompanyDto dto) {
        if (dto.getUuid() == null || dto.getCompanyId() == null) {
            throw new IllegalArgumentException("uuid and companyId are required");
        }
    }
}

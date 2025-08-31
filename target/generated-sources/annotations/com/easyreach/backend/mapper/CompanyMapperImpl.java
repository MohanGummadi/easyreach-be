package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;
import com.easyreach.backend.entity.Company;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:19+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public Company toEntity(CompanyRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Company.CompanyBuilder company = Company.builder();

        company.uuid( dto.getUuid() );
        company.companyCode( dto.getCompanyCode() );
        company.companyName( dto.getCompanyName() );
        company.companyContactNo( dto.getCompanyContactNo() );
        company.companyCoordinates( dto.getCompanyCoordinates() );
        company.companyLocation( dto.getCompanyLocation() );
        company.companyRegistrationDate( dto.getCompanyRegistrationDate() );
        company.ownerName( dto.getOwnerName() );
        company.ownerMobileNo( dto.getOwnerMobileNo() );
        company.ownerEmailAddress( dto.getOwnerEmailAddress() );
        company.ownerDob( dto.getOwnerDob() );
        company.isActive( dto.getIsActive() );
        company.isSynced( dto.getIsSynced() );
        company.createdBy( dto.getCreatedBy() );
        company.createdAt( dto.getCreatedAt() );
        company.updatedBy( dto.getUpdatedBy() );
        company.updatedAt( dto.getUpdatedAt() );

        return company.build();
    }

    @Override
    public CompanyResponseDto toDto(Company entity) {
        if ( entity == null ) {
            return null;
        }

        CompanyResponseDto companyResponseDto = new CompanyResponseDto();

        companyResponseDto.setUuid( entity.getUuid() );
        companyResponseDto.setCompanyCode( entity.getCompanyCode() );
        companyResponseDto.setCompanyName( entity.getCompanyName() );
        companyResponseDto.setCompanyContactNo( entity.getCompanyContactNo() );
        companyResponseDto.setCompanyCoordinates( entity.getCompanyCoordinates() );
        companyResponseDto.setCompanyLocation( entity.getCompanyLocation() );
        companyResponseDto.setCompanyRegistrationDate( entity.getCompanyRegistrationDate() );
        companyResponseDto.setOwnerName( entity.getOwnerName() );
        companyResponseDto.setOwnerMobileNo( entity.getOwnerMobileNo() );
        companyResponseDto.setOwnerEmailAddress( entity.getOwnerEmailAddress() );
        companyResponseDto.setOwnerDob( entity.getOwnerDob() );
        companyResponseDto.setIsActive( entity.getIsActive() );
        companyResponseDto.setIsSynced( entity.getIsSynced() );
        companyResponseDto.setCreatedBy( entity.getCreatedBy() );
        companyResponseDto.setCreatedAt( entity.getCreatedAt() );
        companyResponseDto.setUpdatedBy( entity.getUpdatedBy() );
        companyResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return companyResponseDto;
    }

    @Override
    public void update(Company entity, CompanyRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getUuid() != null ) {
            entity.setUuid( dto.getUuid() );
        }
        if ( dto.getCompanyCode() != null ) {
            entity.setCompanyCode( dto.getCompanyCode() );
        }
        if ( dto.getCompanyName() != null ) {
            entity.setCompanyName( dto.getCompanyName() );
        }
        if ( dto.getCompanyContactNo() != null ) {
            entity.setCompanyContactNo( dto.getCompanyContactNo() );
        }
        if ( dto.getCompanyCoordinates() != null ) {
            entity.setCompanyCoordinates( dto.getCompanyCoordinates() );
        }
        if ( dto.getCompanyLocation() != null ) {
            entity.setCompanyLocation( dto.getCompanyLocation() );
        }
        if ( dto.getCompanyRegistrationDate() != null ) {
            entity.setCompanyRegistrationDate( dto.getCompanyRegistrationDate() );
        }
        if ( dto.getOwnerName() != null ) {
            entity.setOwnerName( dto.getOwnerName() );
        }
        if ( dto.getOwnerMobileNo() != null ) {
            entity.setOwnerMobileNo( dto.getOwnerMobileNo() );
        }
        if ( dto.getOwnerEmailAddress() != null ) {
            entity.setOwnerEmailAddress( dto.getOwnerEmailAddress() );
        }
        if ( dto.getOwnerDob() != null ) {
            entity.setOwnerDob( dto.getOwnerDob() );
        }
        if ( dto.getIsActive() != null ) {
            entity.setIsActive( dto.getIsActive() );
        }
        if ( dto.getIsSynced() != null ) {
            entity.setIsSynced( dto.getIsSynced() );
        }
        if ( dto.getCreatedBy() != null ) {
            entity.setCreatedBy( dto.getCreatedBy() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getUpdatedBy() != null ) {
            entity.setUpdatedBy( dto.getUpdatedBy() );
        }
        if ( dto.getUpdatedAt() != null ) {
            entity.setUpdatedAt( dto.getUpdatedAt() );
        }
    }
}

package com.easyreach.backend.service.impl;

import com.easyreach.backend.security.CompanyContext;

public abstract class CompanyScopedService {
    protected String currentCompany() {
        String companyUuid = CompanyContext.getCompanyId();
        if (companyUuid == null) {
            throw new IllegalStateException("Company ID is not set in context");
        }
        return companyUuid;
    }
}


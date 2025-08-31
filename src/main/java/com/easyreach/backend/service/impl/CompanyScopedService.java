package com.easyreach.backend.service.impl;

import com.easyreach.backend.security.CompanyContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CompanyScopedService {
    protected String currentCompany() {
        log.debug("Entering currentCompany");
        String companyUuid = CompanyContext.getCompanyId();
        if (companyUuid == null) {
            log.error("Company ID is not set in context");
            throw new IllegalStateException("Company ID is not set in context");
        }
        log.debug("Exiting currentCompany with companyUuid={}", companyUuid);
        return companyUuid;
    }
}


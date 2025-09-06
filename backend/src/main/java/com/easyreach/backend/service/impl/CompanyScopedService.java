package com.easyreach.backend.service.impl;

import com.easyreach.backend.security.CompanyContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public abstract class CompanyScopedService {
    protected String currentCompany() {
        log.debug("Entering currentCompany");
        String companyId = CompanyContext.getCompanyId();
        if (!StringUtils.hasText(companyId)) {
            log.error("Company ID is not set in context");
            throw new IllegalStateException("Company ID is not set in context");
        }
        log.debug("Exiting currentCompany with companyId={}", companyId);
        return companyId;
    }
}

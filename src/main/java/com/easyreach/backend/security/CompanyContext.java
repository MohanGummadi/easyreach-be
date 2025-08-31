package com.easyreach.backend.security;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CompanyContext {
    private static final ThreadLocal<String> CURRENT_COMPANY = new ThreadLocal<>();

    private CompanyContext() {}

    public static void setCompanyId(String companyId) {
        log.debug("Setting companyId={} on thread {}", companyId, Thread.currentThread().getName());
        CURRENT_COMPANY.set(companyId);
    }

    public static String getCompanyId() {
        return CURRENT_COMPANY.get();
    }

    public static void clear() {
        log.debug("Clearing companyId for thread {}", Thread.currentThread().getName());
        CURRENT_COMPANY.remove();
    }
}


package com.easyreach.backend.security;

public final class CompanyContext {
    private static final ThreadLocal<String> CURRENT_COMPANY = new ThreadLocal<>();

    private CompanyContext() {}

    public static void setCompanyId(String companyId) {
        CURRENT_COMPANY.set(companyId);
    }

    public static String getCompanyId() {
        return CURRENT_COMPANY.get();
    }

    public static String getRequiredCompanyId() {
        String companyId = CURRENT_COMPANY.get();
        if (companyId == null) {
            throw new IllegalStateException("Company ID is not set in context");
        }
        return companyId;
    }

    public static void clear() {
        CURRENT_COMPANY.remove();
    }
}


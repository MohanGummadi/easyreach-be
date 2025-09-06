package com.easyreach.backend.service;

import com.easyreach.backend.security.CompanyContext;
import com.easyreach.backend.service.impl.CompanyScopedService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyScopedServiceTest {

    static class TestService extends CompanyScopedService {
        String call() { return currentCompany(); }
    }

    @AfterEach
    void tearDown() {
        CompanyContext.clear();
    }

    @Test
    void returnsCompanyIdWhenSet() {
        CompanyContext.setCompanyId("cmp-123");
        TestService svc = new TestService();
        assertEquals("cmp-123", svc.call());
    }

    @Test
    void throwsWhenCompanyIdMissing() {
        TestService svc = new TestService();
        assertThrows(IllegalStateException.class, svc::call);
    }
}

package com.easyreach.backend.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyContextTest {

    @AfterEach
    void tearDown() {
        CompanyContext.clear();
    }

    @Test
    void getCompanyIdReturnsNullWhenNotSet() {
        assertThat(CompanyContext.getCompanyId()).isNull();
    }

    @Test
    void setAndGetCompanyId() {
        CompanyContext.setCompanyId("cmp");
        assertThat(CompanyContext.getCompanyId()).isEqualTo("cmp");
    }

    @Test
    void clearRemovesCompanyId() {
        CompanyContext.setCompanyId("cmp");
        CompanyContext.clear();
        assertThat(CompanyContext.getCompanyId()).isNull();
    }

    @Test
    void companyIdIsThreadLocal() throws InterruptedException {
        CompanyContext.setCompanyId("main");
        final String[] fromThread = new String[1];
        Thread thread = new Thread(() -> {
            fromThread[0] = CompanyContext.getCompanyId();
            CompanyContext.setCompanyId("other");
        });
        thread.start();
        thread.join();
        assertThat(fromThread[0]).isNull();
        assertThat(CompanyContext.getCompanyId()).isEqualTo("main");
    }
}

package com.easyreach.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class CompanyInterceptor implements HandlerInterceptor {
    private static final String HEADER = "X-Company-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String companyId = request.getHeader(HEADER);
        if (companyId == null || companyId.isBlank()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), HEADER + " header is required");
            return false;
        }
        CompanyContext.setCompanyId(companyId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        CompanyContext.clear();
    }
}


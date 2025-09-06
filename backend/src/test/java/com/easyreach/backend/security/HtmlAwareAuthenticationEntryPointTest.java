package com.easyreach.backend.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HtmlAwareAuthenticationEntryPointTest {
    private HtmlAwareAuthenticationEntryPoint entryPoint;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private AuthenticationException exception;

    @BeforeEach
    void setUp() {
        entryPoint = new HtmlAwareAuthenticationEntryPoint();
    }

    @Test
    void htmlRequest_redirectsToLogin() throws IOException, ServletException {
        when(request.getHeader("Accept")).thenReturn("text/html");
        when(request.getRequestURI()).thenReturn("/path");
        when(request.getQueryString()).thenReturn("a=1");

        entryPoint.commence(request, response, exception);

        verify(response).sendRedirect("/login?redirect=%2Fpath%3Fa%3D1");
    }

    @Test
    void apiRequest_sendsUnauthorized() throws IOException, ServletException {
        when(request.getHeader("Accept")).thenReturn("application/json");
        when(exception.getMessage()).thenReturn("msg");

        entryPoint.commence(request, response, exception);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "msg");
    }
}

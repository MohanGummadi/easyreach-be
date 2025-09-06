package com.easyreach.backend.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Authentication entry point that redirects HTML requests to the login page
 * while letting API callers receive a 401 Unauthorized response.
 */
public class HtmlAwareAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("text/html")) {
            String requestUri = request.getRequestURI();
            String query = request.getQueryString();
            String original = requestUri + (query != null ? "?" + query : "");
            String redirect = URLEncoder.encode(original, StandardCharsets.UTF_8);
            response.sendRedirect("/login?redirect=" + redirect);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }
    }
}

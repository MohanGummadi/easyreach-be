package com.easyreach.tests.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthClientTest {

    @Test
    void login() {
        String token = AuthClient.getToken();
        assertNotNull(token);
    }
}

package com.easyreach.tests.auth;

import com.easyreach.tests.config.TestConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@Tag("e2e")
public class AuthClientTest {
    @Test
    void loginAndRefresh() {
        String email = TestConfig.getAuthEmail();
        String password = TestConfig.getAuthPassword();
        given().baseUri(TestConfig.getBaseUrl())
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                .post("/auth/login")
                .then().statusCode(200).body("accessToken", notNullValue());
    }
}

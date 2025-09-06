package com.easyreach.tests.users;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.IdStore;
import com.easyreach.tests.core.SampleData;
import static com.easyreach.tests.core.EntityHelper.ensureCompany;
import static com.easyreach.tests.core.EntityHelper.ensureUser;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
@TestMethodOrder(OrderAnnotation.class)
public class UserIT extends BaseIT {
    @Test
    @Order(1)
    void shouldCreateUser() {
        String companyId = ensureCompany();
        Map<String, Object> body = SampleData.userRequest(companyId);
        Response r = given().spec(spec).body(body).post("/api/users");
        r.then().statusCode(200);
        String id = r.jsonPath().getString("data.id");
        IdStore.put("userId", id);
    }

    @Test
    @Order(2)
    void shouldGetUser() {
        String id = ensureUser();
        given().spec(spec).get("/api/users/" + id)
                .then().statusCode(200).body("data.id", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListUsers() {
        given().spec(spec).get("/api/users" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdateUser() {
        String id = ensureUser();
        String companyId = ensureCompany();
        Map<String, Object> body = SampleData.userRequest(companyId);
        body.put("id", id);
        body.put("name", "Updated User");
        given().spec(spec).body(body).put("/api/users/" + id)
                .then().statusCode(200)
                .body("data.name", equalTo("Updated User"));
    }

    @Test
    @Order(5)
    void shouldDeleteUser() {
        String id = ensureUser();
        given().spec(spec).delete("/api/users/" + id)
                .then().statusCode(200);
    }
}

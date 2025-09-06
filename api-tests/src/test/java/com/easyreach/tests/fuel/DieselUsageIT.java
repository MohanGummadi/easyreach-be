package com.easyreach.tests.fuel;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.IdStore;
import com.easyreach.tests.core.SampleData;
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
public class DieselUsageIT extends BaseIT {
    @Test
    @Order(1)
    void shouldCreateDieselUsage() {
        String companyId = ensureCompany();
        Map<String, Object> body = SampleData.dieselUsageRequest(companyId, "VehicleA");
        Response r = given().spec(spec).body(body).post("/api/diesel-usage");
        r.then().statusCode(200);
        String id = r.jsonPath().getString("data.dieselUsageId");
        IdStore.put("dieselUsageId", id);
    }

    @Test
    @Order(2)
    void shouldGetDieselUsage() {
        String id = IdStore.get("dieselUsageId");
        given().spec(spec).get("/api/diesel-usage/" + id)
                .then().statusCode(200).body("data.dieselUsageId", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListDieselUsage() {
        given().spec(spec).get("/api/diesel-usage" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdateDieselUsage() {
        String id = IdStore.get("dieselUsageId");
        String companyId = IdStore.get("companyUuid");
        Map<String, Object> body = SampleData.dieselUsageRequest(companyId, "VehicleA");
        body.put("dieselUsageId", id);
        body.put("liters", 60);
        given().spec(spec).body(body).put("/api/diesel-usage/" + id)
                .then().statusCode(200)
                .body("data.liters", equalTo(60));
    }

    @Test
    @Order(5)
    void shouldDeleteDieselUsage() {
        String id = IdStore.get("dieselUsageId");
        given().spec(spec).delete("/api/diesel-usage/" + id)
                .then().statusCode(200);
    }

    private String ensureCompany() {
        String id = IdStore.get("companyUuid");
        if (id == null) {
            Map<String, Object> body = SampleData.companyRequest();
            Response r = given().spec(spec).body(body).post("/api/companies");
            id = r.jsonPath().getString("data.uuid");
            IdStore.put("companyUuid", id);
        }
        return id;
    }
}

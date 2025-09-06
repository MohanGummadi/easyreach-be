package com.easyreach.tests.payers;

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
public class PayerIT extends BaseIT {
    @Test
    @Order(1)
    void shouldCreatePayer() {
        String companyId = ensureCompany();
        Map<String, Object> body = SampleData.payerRequest(companyId);
        Response r = given().spec(spec).body(body).post("/api/payers");
        r.then().statusCode(200);
        String id = r.jsonPath().getString("data.payerId");
        IdStore.put("payerId", id);
    }

    @Test
    @Order(2)
    void shouldGetPayer() {
        String id = IdStore.get("payerId");
        given().spec(spec).get("/api/payers/" + id)
                .then().statusCode(200).body("data.payerId", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListPayers() {
        given().spec(spec).get("/api/payers" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdatePayer() {
        String id = IdStore.get("payerId");
        String companyId = IdStore.get("companyUuid");
        Map<String, Object> body = SampleData.payerRequest(companyId);
        body.put("payerId", id);
        body.put("payerName", "Updated Payer");
        given().spec(spec).body(body).put("/api/payers/" + id)
                .then().statusCode(200)
                .body("data.payerName", equalTo("Updated Payer"));
    }

    @Test
    @Order(5)
    void shouldDeletePayer() {
        String id = IdStore.get("payerId");
        given().spec(spec).delete("/api/payers/" + id)
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

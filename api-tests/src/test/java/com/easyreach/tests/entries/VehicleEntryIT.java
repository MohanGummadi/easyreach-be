package com.easyreach.tests.entries;

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
public class VehicleEntryIT extends BaseIT {
    @Test
    @Order(1)
    void shouldCreateVehicleEntry() {
        String companyId = ensureCompany();
        String payerId = ensurePayer();
        Map<String, Object> body = SampleData.vehicleEntryRequest(companyId, payerId, "TRUCK");
        Response r = given().spec(spec).body(body).post("/api/vehicle-entries");
        r.then().statusCode(200);
        String id = r.jsonPath().getString("data.entryId");
        IdStore.put("entryId", id);
    }

    @Test
    @Order(2)
    void shouldGetVehicleEntry() {
        String id = IdStore.get("entryId");
        given().spec(spec).get("/api/vehicle-entries/" + id)
                .then().statusCode(200).body("data.entryId", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListVehicleEntries() {
        given().spec(spec).get("/api/vehicle-entries" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdateVehicleEntry() {
        String id = IdStore.get("entryId");
        String companyId = IdStore.get("companyUuid");
        String payerId = IdStore.get("payerId");
        Map<String, Object> body = SampleData.vehicleEntryRequest(companyId, payerId, "TRUCK");
        body.put("entryId", id);
        body.put("amount", 1500);
        given().spec(spec).body(body).put("/api/vehicle-entries/" + id)
                .then().statusCode(200)
                .body("data.amount", equalTo(1500));
    }

    @Test
    @Order(5)
    void shouldDeleteVehicleEntry() {
        String id = IdStore.get("entryId");
        given().spec(spec).delete("/api/vehicle-entries/" + id)
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

    private String ensurePayer() {
        String id = IdStore.get("payerId");
        if (id == null) {
            String companyId = ensureCompany();
            Map<String, Object> body = SampleData.payerRequest(companyId);
            Response r = given().spec(spec).body(body).post("/api/payers");
            id = r.jsonPath().getString("data.payerId");
            IdStore.put("payerId", id);
        }
        return id;
    }
}

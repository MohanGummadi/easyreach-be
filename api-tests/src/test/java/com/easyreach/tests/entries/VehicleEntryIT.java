package com.easyreach.tests.entries;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.IdStore;
import com.easyreach.tests.core.SampleData;
import static com.easyreach.tests.core.EntityHelper.ensureCompany;
import static com.easyreach.tests.core.EntityHelper.ensurePayer;
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
        String id = ensureVehicleEntry();
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
        String id = ensureVehicleEntry();
        String companyId = ensureCompany();
        String payerId = ensurePayer();
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
        String id = ensureVehicleEntry();
        given().spec(spec).delete("/api/vehicle-entries/" + id)
                .then().statusCode(200);
    }

    private String ensureVehicleEntry() {
        String id = IdStore.get("entryId");
        if (id == null) {
            String companyId = ensureCompany();
            String payerId = ensurePayer();
            Map<String, Object> body = SampleData.vehicleEntryRequest(companyId, payerId, "TRUCK");
            Response r = given().spec(spec).body(body).post("/api/vehicle-entries");
            id = r.jsonPath().getString("data.entryId");
            IdStore.put("entryId", id);
        }
        return id;
    }
}

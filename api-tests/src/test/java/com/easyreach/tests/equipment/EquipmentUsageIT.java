package com.easyreach.tests.equipment;

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
public class EquipmentUsageIT extends BaseIT {
    @Test
    @Order(1)
    void shouldCreateEquipmentUsage() {
        String companyId = ensureCompany();
        Map<String, Object> body = SampleData.equipmentUsageRequest(companyId);
        Response r = given().spec(spec).body(body).post("/api/equipment-usage");
        r.then().statusCode(200);
        String id = r.jsonPath().getString("data.equipmentUsageId");
        IdStore.put("equipmentUsageId", id);
    }

    @Test
    @Order(2)
    void shouldGetEquipmentUsage() {
        String id = IdStore.get("equipmentUsageId");
        given().spec(spec).get("/api/equipment-usage/" + id)
                .then().statusCode(200).body("data.equipmentUsageId", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListEquipmentUsage() {
        given().spec(spec).get("/api/equipment-usage" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdateEquipmentUsage() {
        String id = IdStore.get("equipmentUsageId");
        String companyId = IdStore.get("companyUuid");
        Map<String, Object> body = SampleData.equipmentUsageRequest(companyId);
        body.put("equipmentUsageId", id);
        body.put("endKm", 20);
        given().spec(spec).body(body).put("/api/equipment-usage/" + id)
                .then().statusCode(200)
                .body("data.endKm", equalTo(20));
    }

    @Test
    @Order(5)
    void shouldDeleteEquipmentUsage() {
        String id = IdStore.get("equipmentUsageId");
        given().spec(spec).delete("/api/equipment-usage/" + id)
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

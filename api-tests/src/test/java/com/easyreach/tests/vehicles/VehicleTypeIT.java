package com.easyreach.tests.vehicles;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.IdStore;
import com.easyreach.tests.core.SampleData;
import static com.easyreach.tests.core.EntityHelper.ensureCompany;
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
public class VehicleTypeIT extends BaseIT {
    @Test
    @Order(1)
    void shouldCreateVehicleType() {
        String companyId = ensureCompany();
        Map<String, Object> body = SampleData.vehicleTypeRequest(companyId);
        Response r = given().spec(spec).body(body).post("/api/vehicle-types");
        r.then().statusCode(200);
        String id = r.jsonPath().getString("data.id");
        IdStore.put("vehicleTypeId", id);
    }

    @Test
    @Order(2)
    void shouldGetVehicleType() {
        String id = ensureVehicleType();
        given().spec(spec).get("/api/vehicle-types/" + id)
                .then().statusCode(200).body("data.id", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListVehicleTypes() {
        given().spec(spec).get("/api/vehicle-types" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdateVehicleType() {
        String id = ensureVehicleType();
        String companyId = ensureCompany();
        Map<String, Object> body = SampleData.vehicleTypeRequest(companyId);
        body.put("id", id);
        body.put("vehicleType", "UpdatedType");
        given().spec(spec).body(body).put("/api/vehicle-types/" + id)
                .then().statusCode(200)
                .body("data.vehicleType", equalTo("UpdatedType"));
    }

    @Test
    @Order(5)
    void shouldDeleteVehicleType() {
        String id = ensureVehicleType();
        given().spec(spec).delete("/api/vehicle-types/" + id)
                .then().statusCode(200);
    }

    private String ensureVehicleType() {
        String id = IdStore.get("vehicleTypeId");
        if (id == null) {
            String companyId = ensureCompany();
            Map<String, Object> body = SampleData.vehicleTypeRequest(companyId);
            Response r = given().spec(spec).body(body).post("/api/vehicle-types");
            id = r.jsonPath().getString("data.id");
            IdStore.put("vehicleTypeId", id);
        }
        return id;
    }
}

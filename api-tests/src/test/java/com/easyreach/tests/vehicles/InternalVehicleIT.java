package com.easyreach.tests.vehicles;

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
public class InternalVehicleIT extends BaseIT {
    @Test
    @Order(1)
    void shouldCreateInternalVehicle() {
        String companyId = IdStore.get("companyUuid");
        if (companyId == null) {
            companyId = createCompany();
        }
        Map<String, Object> body = SampleData.internalVehicleRequest(companyId);
        Response r = given().spec(spec).body(body).post("/api/internal-vehicles");
        r.then().statusCode(200);
        String id = r.jsonPath().getString("data.vehicleId");
        IdStore.put("internalVehicleId", id);
    }

    @Test
    @Order(2)
    void shouldGetInternalVehicle() {
        String id = IdStore.get("internalVehicleId");
        given().spec(spec).get("/api/internal-vehicles/" + id)
                .then().statusCode(200).body("data.vehicleId", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListInternalVehicles() {
        given().spec(spec).get("/api/internal-vehicles" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdateInternalVehicle() {
        String id = IdStore.get("internalVehicleId");
        String companyId = IdStore.get("companyUuid");
        Map<String, Object> body = SampleData.internalVehicleRequest(companyId);
        body.put("vehicleId", id);
        body.put("vehicleName", "UpdatedVehicle");
        given().spec(spec).body(body).put("/api/internal-vehicles/" + id)
                .then().statusCode(200)
                .body("data.vehicleName", equalTo("UpdatedVehicle"));
    }

    @Test
    @Order(5)
    void shouldDeleteInternalVehicle() {
        String id = IdStore.get("internalVehicleId");
        given().spec(spec).delete("/api/internal-vehicles/" + id)
                .then().statusCode(200);
    }

    private String createCompany() {
        Map<String, Object> body = SampleData.companyRequest();
        Response r = given().spec(spec).body(body).post("/api/companies");
        String id = r.jsonPath().getString("data.uuid");
        IdStore.put("companyUuid", id);
        return id;
    }
}

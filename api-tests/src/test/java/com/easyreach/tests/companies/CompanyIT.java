package com.easyreach.tests.companies;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.IdStore;
import com.easyreach.tests.core.SampleData;
import static com.easyreach.tests.core.EntityHelper.ensureCompany;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
@TestMethodOrder(OrderAnnotation.class)
public class CompanyIT extends BaseIT {
    private static String companyId;

    @Test
    @Order(1)
    void shouldCreateCompany() {
        Map<String, Object> body = SampleData.companyRequest();
        Response r = given().spec(spec).body(body).post("/api/companies");
        r.then().statusCode(200);
        companyId = r.jsonPath().getString("data.uuid");
        assertThat(companyId, notNullValue());
        IdStore.put("companyUuid", companyId);
    }

    @Test
    @Order(2)
    void shouldGetCompany() {
        String id = ensureCompany();
        Response r = given().spec(spec).get("/api/companies/" + id);
        r.then().statusCode(200).body("data.uuid", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListCompanies() {
        given().spec(spec).get("/api/companies" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdateCompany() {
        String id = ensureCompany();
        Map<String, Object> body = SampleData.companyRequest();
        body.put("uuid", id);
        body.put("companyName", "Updated Co");
        given().spec(spec).body(body).put("/api/companies/" + id)
                .then().statusCode(200)
                .body("data.companyName", equalTo("Updated Co"));
    }

    @Test
    @Order(5)
    void shouldDeleteCompany() {
        String id = ensureCompany();
        given().spec(spec).delete("/api/companies/" + id)
                .then().statusCode(200);
    }
}

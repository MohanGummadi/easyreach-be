package com.easyreach.tests.companies;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.IdStore;
import com.easyreach.tests.core.SampleData;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(OrderAnnotation.class)
@Tag("e2e")
class CompanyIT extends BaseIT {

    @Test
    @Order(1)
    void createCompany() throws Exception {
        String json = givenAuth()
                .body(SampleData.company())
                .post("/companies")
                .then()
                .statusCode(201)
                .extract().asString();
        JsonNode node = MAPPER.readTree(json);
        String id = node.get("id").asText();
        IdStore.put("company", id);
        assertThat(node.get("name").asText(), equalTo(node.get("name").asText()));
    }

    @Test
    @Order(2)
    void getCompany() {
        String id = IdStore.get("company");
        givenAuth()
                .get("/companies/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id));
    }

    @Test
    @Order(3)
    void listCompanies() {
        givenAuth()
                .queryParams(pageable())
                .get("/companies")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    void updateCompany() {
        String id = IdStore.get("company");
        givenAuth()
                .body(SampleData.company())
                .put("/companies/{id}", id)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(5)
    void deleteCompany() {
        String id = IdStore.get("company");
        givenAuth()
                .delete("/companies/{id}", id)
                .then()
                .statusCode(204);
    }
}

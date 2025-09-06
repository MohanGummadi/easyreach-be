package com.easyreach.tests.expenses;

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
public class ExpenseMasterIT extends BaseIT {
    @Test
    @Order(1)
    void shouldCreateExpenseMaster() {
        String companyId = ensureCompany();
        Map<String, Object> body = SampleData.expenseMasterRequest(companyId);
        Response r = given().spec(spec).body(body).post("/api/expense-master");
        r.then().statusCode(200);
        String id = r.jsonPath().getString("data.id");
        IdStore.put("expenseMasterId", id);
    }

    @Test
    @Order(2)
    void shouldGetExpenseMaster() {
        String id = IdStore.get("expenseMasterId");
        given().spec(spec).get("/api/expense-master/" + id)
                .then().statusCode(200).body("data.id", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListExpenseMasters() {
        given().spec(spec).get("/api/expense-master" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdateExpenseMaster() {
        String id = IdStore.get("expenseMasterId");
        String companyId = IdStore.get("companyUuid");
        Map<String, Object> body = SampleData.expenseMasterRequest(companyId);
        body.put("id", id);
        body.put("expenseName", "UpdatedExpense");
        given().spec(spec).body(body).put("/api/expense-master/" + id)
                .then().statusCode(200)
                .body("data.expenseName", equalTo("UpdatedExpense"));
    }

    @Test
    @Order(5)
    void shouldDeleteExpenseMaster() {
        String id = IdStore.get("expenseMasterId");
        given().spec(spec).delete("/api/expense-master/" + id)
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

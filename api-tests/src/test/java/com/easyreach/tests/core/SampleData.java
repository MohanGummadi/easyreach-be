package com.easyreach.tests.core;

import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class SampleData {
    private static final Faker FAKER = new Faker();

    public static Map<String, Object> companyRequest() {
        Map<String, Object> m = new HashMap<>();
        m.put("uuid", UUID.randomUUID().toString());
        m.put("companyCode", FAKER.number().digits(5));
        m.put("companyName", FAKER.company().name());
        m.put("companyContactNo", FAKER.phoneNumber().subscriberNumber(10));
        m.put("companyLocation", FAKER.address().city());
        m.put("companyRegistrationDate", LocalDate.now().toString());
        m.put("ownerName", FAKER.name().fullName());
        m.put("ownerMobileNo", FAKER.phoneNumber().subscriberNumber(10));
        m.put("ownerEmailAddress", FAKER.internet().emailAddress());
        m.put("ownerDob", LocalDate.now().minusYears(30).toString());
        m.put("isActive", true);
        m.put("isSynced", true);
        m.put("createdAt", Instant.now().toString());
        m.put("updatedAt", Instant.now().toString());
        m.put("deleted", false);
        return m;
    }

    public static Map<String, Object> vehicleTypeRequest(String companyUuid) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", UUID.randomUUID().toString());
        m.put("vehicleType", FAKER.ancient().god());
        m.put("type", "GENERAL");
        m.put("companyUuid", companyUuid);
        m.put("isSynced", true);
        m.put("createdAt", Instant.now().toString());
        m.put("updatedAt", Instant.now().toString());
        m.put("deleted", false);
        return m;
    }

    public static Map<String, Object> internalVehicleRequest(String companyUuid) {
        Map<String, Object> m = new HashMap<>();
        m.put("vehicleId", UUID.randomUUID().toString());
        m.put("vehicleName", FAKER.vehicle().model());
        m.put("vehicleType", "TRUCK");
        m.put("isActive", true);
        m.put("companyUuid", companyUuid);
        m.put("isSynced", true);
        m.put("createdAt", Instant.now().toString());
        m.put("updatedAt", Instant.now().toString());
        m.put("deleted", false);
        return m;
    }

    public static Map<String, Object> vehicleEntryRequest(String companyUuid, String payerId, String vehicleType) {
        Map<String, Object> m = new HashMap<>();
        m.put("entryId", UUID.randomUUID().toString());
        m.put("companyUuid", companyUuid);
        m.put("payerId", payerId);
        m.put("vehicleNumber", "MH" + FAKER.number().digits(8));
        m.put("vehicleType", vehicleType);
        m.put("fromAddress", FAKER.address().streetAddress());
        m.put("toAddress", FAKER.address().streetAddress());
        m.put("driverName", FAKER.name().fullName());
        m.put("driverContactNo", FAKER.phoneNumber().subscriberNumber(10));
        m.put("commission", BigDecimal.valueOf(10));
        m.put("beta", BigDecimal.valueOf(5));
        m.put("amount", BigDecimal.valueOf(1000));
        m.put("paytype", "CASH");
        m.put("entryDate", LocalDate.now().toString());
        m.put("entryTime", Instant.now().toString());
        m.put("paidAmount", BigDecimal.ZERO);
        m.put("pendingAmt", BigDecimal.valueOf(1000));
        m.put("isSettled", false);
        m.put("isSynced", true);
        m.put("createdAt", Instant.now().toString());
        m.put("updatedAt", Instant.now().toString());
        m.put("deleted", false);
        return m;
    }

    public static Map<String, Object> dailyExpenseRequest(String companyUuid) {
        Map<String, Object> m = new HashMap<>();
        m.put("expenseId", UUID.randomUUID().toString());
        m.put("expenseType", "FUEL");
        m.put("expenseAmount", BigDecimal.valueOf(100));
        m.put("expenseDate", Instant.now().toString());
        m.put("isPaid", true);
        m.put("companyUuid", companyUuid);
        m.put("isSynced", true);
        m.put("createdAt", Instant.now().toString());
        m.put("updatedAt", Instant.now().toString());
        m.put("deleted", false);
        return m;
    }

    public static Map<String, Object> dieselUsageRequest(String companyUuid, String vehicleName) {
        Map<String, Object> m = new HashMap<>();
        m.put("dieselUsageId", UUID.randomUUID().toString());
        m.put("vehicleName", vehicleName);
        m.put("date", Instant.now().toString());
        m.put("liters", BigDecimal.valueOf(50));
        m.put("companyUuid", companyUuid);
        m.put("isSynced", true);
        m.put("createdAt", Instant.now().toString());
        m.put("updatedAt", Instant.now().toString());
        m.put("deleted", false);
        return m;
    }

    public static Map<String, Object> equipmentUsageRequest(String companyUuid) {
        Map<String, Object> m = new HashMap<>();
        m.put("equipmentUsageId", UUID.randomUUID().toString());
        m.put("equipmentName", FAKER.job().field());
        m.put("equipmentType", "GENERIC");
        m.put("startKm", BigDecimal.valueOf(0));
        m.put("endKm", BigDecimal.valueOf(10));
        m.put("startTime", Instant.now().toString());
        m.put("endTime", Instant.now().toString());
        m.put("date", Instant.now().toString());
        m.put("companyUuid", companyUuid);
        m.put("isSynced", true);
        m.put("createdAt", Instant.now().toString());
        m.put("updatedAt", Instant.now().toString());
        m.put("deleted", false);
        return m;
    }

    public static Map<String, Object> expenseMasterRequest(String companyUuid) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", UUID.randomUUID().toString());
        m.put("expenseName", FAKER.commerce().productName());
        m.put("companyUuid", companyUuid);
        m.put("isSynced", true);
        m.put("createdAt", Instant.now().toString());
        m.put("updatedAt", Instant.now().toString());
        m.put("deleted", false);
        return m;
    }

    public static Map<String, Object> payerRequest(String companyUuid) {
        Map<String, Object> m = new HashMap<>();
        m.put("payerId", UUID.randomUUID().toString());
        m.put("payerName", FAKER.name().fullName());
        m.put("mobileNo", FAKER.phoneNumber().subscriberNumber(10));
        m.put("companyUuid", companyUuid);
        m.put("isSynced", true);
        m.put("createdAt", Instant.now().toString());
        m.put("updatedAt", Instant.now().toString());
        m.put("deleted", false);
        return m;
    }

    public static Map<String, Object> payerSettlementRequest(String companyUuid, String payerId) {
        Map<String, Object> m = new HashMap<>();
        m.put("settlementId", UUID.randomUUID().toString());
        m.put("payerId", payerId);
        m.put("amount", BigDecimal.valueOf(100));
        m.put("date", Instant.now().toString());
        m.put("companyUuid", companyUuid);
        m.put("isSynced", true);
        m.put("createdAt", Instant.now().toString());
        m.put("updatedAt", Instant.now().toString());
        m.put("deleted", false);
        return m;
    }

    public static Map<String, Object> refreshTokenRequest(String userId) {
        Map<String, Object> m = new HashMap<>();
        m.put("jti", UUID.randomUUID().toString());
        m.put("userId", userId);
        m.put("issuedAt", Instant.now().toString());
        m.put("expiresAt", Instant.now().plusSeconds(3600).toString());
        return m;
    }

    public static Map<String, Object> userRequest(String companyUuid) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", UUID.randomUUID().toString());
        m.put("employeeId", FAKER.idNumber().valid());
        m.put("email", FAKER.internet().emailAddress());
        m.put("mobileNo", FAKER.phoneNumber().subscriberNumber(10));
        m.put("password", "password");
        m.put("role", "USER");
        m.put("name", FAKER.name().fullName());
        m.put("companyUuid", companyUuid);
        m.put("companyCode", FAKER.number().digits(5));
        m.put("isActive", true);
        return m;
    }

    public static Map<String, Object> addPaymentRequest() {
        Map<String, Object> m = new HashMap<>();
        m.put("amount", BigDecimal.valueOf(100));
        m.put("receivedBy", FAKER.name().fullName());
        m.put("when", Instant.now().toString());
        return m;
    }

    public static Map<String, Object> applyPaymentRequest() {
        Map<String, Object> m = new HashMap<>();
        m.put("amount", BigDecimal.valueOf(100));
        m.put("settlementType", "CASH");
        return m;
    }

    public static Map<String, Object> syncRequest(Map<String, Object> company, Map<String, Object> payer,
                                                 Map<String, Object> vehicleType, Map<String, Object> vehicleEntry) {
        Map<String, Object> m = new HashMap<>();
        m.put("companies", Collections.singletonList(company));
        m.put("payers", Collections.singletonList(payer));
        m.put("vehicleTypes", Collections.singletonList(vehicleType));
        m.put("vehicleEntries", Collections.singletonList(vehicleEntry));
        return m;
    }
}

package com.easyreach.backend.service.impl;

import com.easyreach.backend.auth.entity.UserAdapter;
import com.easyreach.backend.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SyncDownloadServiceImpl implements SyncDownloadService {

    private final CompanyService companyService;
    private final DailyExpenseService dailyExpenseService;
    private final DieselUsageService dieselUsageService;
    private final EquipmentUsageService equipmentUsageService;
    private final ExpenseMasterService expenseMasterService;
    private final InternalVehicleService internalVehicleService;
    private final PayerService payerService;
    private final PayerSettlementService payerSettlementService;
    private final VehicleEntryService vehicleEntryService;
    private final VehicleTypeService vehicleTypeService;
    private final UserService userService;

    @Override
    public Map<String, Object> downloadChanges(String sinceCursor, List<String> entities, Integer limit) {
        log.debug("Entering downloadChanges sinceCursor={} entities={} limit={}", sinceCursor, entities, limit);
        int fetchLimit = limit != null ? limit : 100;

        OffsetDateTime cursor;
        try {
            cursor = sinceCursor != null
                    ? OffsetDateTime.parse(sinceCursor)
                    : OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
        } catch (DateTimeParseException ex) {
            log.warn("Invalid sinceCursor {}, defaulting to epoch", sinceCursor);
            cursor = OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
        }

        UserAdapter adapter = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String companyUuid = adapter.getDomainUser().getCompanyUuid();

        Map<String, Object> response = new HashMap<>();
        OffsetDateTime cursorEnd = cursor;
        boolean hasMore = false;

        List<String> targets;
        if (entities == null || entities.isEmpty()) {
            targets = List.of(
                    "companies",
                    "dailyExpenses",
                    "dieselUsages",
                    "equipmentUsages",
                    "expenseMasters",
                    "internalVehicles",
                    "payers",
                    "payerSettlements",
                    "vehicleEntries",
                    "vehicleTypes",
                    "users"
            );
        } else {
            targets = entities;
        }

        for (String entity : targets) {
            Map<String, Object> data = switch (entity) {
                case "companies" -> companyService.fetchChangesSince(companyUuid, cursor, fetchLimit);
                case "dailyExpenses" -> dailyExpenseService.fetchChangesSince(companyUuid, cursor, fetchLimit);
                case "dieselUsages" -> dieselUsageService.fetchChangesSince(companyUuid, cursor, fetchLimit);
                case "equipmentUsages" -> equipmentUsageService.fetchChangesSince(companyUuid, cursor, fetchLimit);
                case "expenseMasters" -> expenseMasterService.fetchChangesSince(companyUuid, cursor, fetchLimit);
                case "internalVehicles" -> internalVehicleService.fetchChangesSince(companyUuid, cursor, fetchLimit);
                case "payers" -> payerService.fetchChangesSince(companyUuid, cursor, fetchLimit);
                case "payerSettlements" -> payerSettlementService.fetchChangesSince(companyUuid, cursor, fetchLimit);
                case "vehicleEntries" -> vehicleEntryService.fetchChangesSince(companyUuid, cursor, fetchLimit);
                case "vehicleTypes" -> vehicleTypeService.fetchChangesSince(companyUuid, cursor, fetchLimit);
                case "users" -> userService.fetchChangesSince(companyUuid, cursor, fetchLimit);
                default -> null;
            };
            if (data == null) {
                log.warn("Unknown entity requested: {}", entity);
                continue;
            }

            List<?> items = (List<?>) data.getOrDefault("items", Collections.emptyList());
            List<?> tombstones = (List<?>) data.getOrDefault("tombstones", Collections.emptyList());
            OffsetDateTime entityCursorEnd = (OffsetDateTime) data.getOrDefault("cursorEnd", cursor);
            boolean entityHasMore = (boolean) data.getOrDefault("hasMore", false);

            Map<String, Object> perEntity = new HashMap<>();
            perEntity.put("items", items);
            perEntity.put("tombstones", tombstones);
            perEntity.put("cursorEnd", entityCursorEnd);
            perEntity.put("hasMore", entityHasMore);
            response.put(entity, perEntity);

            if (entityCursorEnd != null && entityCursorEnd.isAfter(cursorEnd)) {
                cursorEnd = entityCursorEnd;
            }
            if (entityHasMore) {
                hasMore = true;
            }
        }

        response.put("cursorEnd", cursorEnd);
        response.put("hasMore", hasMore);
        log.debug("Exiting downloadChanges with hasMore={} cursorEnd={}", hasMore, cursorEnd);
        return response;
    }

    private OffsetDateTime extractTimestamp(Object obj) {
        log.debug("Extracting timestamp from {}", obj);
        try {
            Method deletedAt = obj.getClass().getMethod("getDeletedAt");
            Object val = deletedAt.invoke(obj);
            if (val instanceof OffsetDateTime odt && odt != null) {
                log.debug("Found deletedAt timestamp={}", odt);
                return odt;
            }
        } catch (Exception ignored) {
        }
        try {
            Method updatedAt = obj.getClass().getMethod("getUpdatedAt");
            Object val = updatedAt.invoke(obj);
            if (val instanceof OffsetDateTime odt) {
                log.debug("Found updatedAt timestamp={}", odt);
                return odt;
            }
        } catch (Exception ignored) {
        }
        log.debug("No timestamp found for {}", obj);
        return null;
    }
}

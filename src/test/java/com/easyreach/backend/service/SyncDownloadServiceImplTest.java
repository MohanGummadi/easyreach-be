package com.easyreach.backend.service;

import com.easyreach.backend.auth.entity.UserAdapter;
import com.easyreach.backend.entity.User;
import com.easyreach.backend.service.impl.SyncDownloadServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.OffsetDateTime;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SyncDownloadServiceImplTest {

    @Mock private CompanyService companyService;
    @Mock private DailyExpenseService dailyExpenseService;
    @Mock private DieselUsageService dieselUsageService;
    @Mock private EquipmentUsageService equipmentUsageService;
    @Mock private ExpenseMasterService expenseMasterService;
    @Mock private InternalVehicleService internalVehicleService;
    @Mock private PayerService payerService;
    @Mock private PayerSettlementService payerSettlementService;
    @Mock private VehicleEntryService vehicleEntryService;
    @Mock private VehicleTypeService vehicleTypeService;
    @Mock private UserService userService;

    @InjectMocks
    private SyncDownloadServiceImpl service;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setCompanyUuid("cmp-1");
        user.setIsActive(true);
        user.setRole("ADMIN");
        user.setPassword("pw");
        UserAdapter adapter = new UserAdapter(user);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(adapter, null));
        SecurityContextHolder.setContext(context);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void aggregatesEntityResponsesAndDeterminesCursor() {
        OffsetDateTime cursor = OffsetDateTime.now().minusDays(1);
        OffsetDateTime c1 = cursor.plusHours(1);
        OffsetDateTime c2 = cursor.plusHours(2);

        when(companyService.fetchChangesSince(eq("cmp-1"), eq(cursor), eq(50)))
                .thenReturn(Map.of("items", List.of(1), "cursorEnd", c1, "hasMore", false));
        when(payerService.fetchChangesSince(eq("cmp-1"), eq(cursor), eq(50)))
                .thenReturn(Map.of("items", List.of(2), "cursorEnd", c2, "hasMore", true));

        Map<String, Object> result = service.downloadChanges(cursor.toString(), List.of("companies", "payers"), 50);

        assertEquals(c2, result.get("cursorEnd"));
        assertEquals(true, result.get("hasMore"));

        Map<String, Object> comp = (Map<String, Object>) result.get("companies");
        assertEquals(List.of(1), comp.get("items"));

        Map<String, Object> pay = (Map<String, Object>) result.get("payers");
        assertEquals(List.of(2), pay.get("items"));
    }

    @Test
    void defaultsCursorToEpochOnInvalidInput() {
        OffsetDateTime epoch = OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
        when(companyService.fetchChangesSince(eq("cmp-1"), eq(epoch), eq(100)))
                .thenReturn(Map.of("items", List.of(), "cursorEnd", epoch, "hasMore", false));

        Map<String, Object> result = service.downloadChanges("bad", List.of("companies"), null);

        assertEquals(epoch, result.get("cursorEnd"));
        verify(companyService).fetchChangesSince(eq("cmp-1"), eq(epoch), eq(100));
    }
}

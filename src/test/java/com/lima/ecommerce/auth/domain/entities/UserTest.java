package com.lima.ecommerce.auth.domain.entities;

import com.lima.ecommerce.auth.domain.enums.Role;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void newCustomerShouldNormalizeEmailAndSetDefaultValues() {
        User user = User.newCustomer("  Ana Silva  ", "  ANA@MAIL.COM  ", "hashed_password");

        assertEquals("Ana Silva", user.getName());
        assertEquals("ana@mail.com", user.getEmail());
        assertEquals("hashed_password", user.getPasswordHash());
        assertEquals(Role.CUSTOMER, user.getRole());
        assertTrue(user.getActive());
        assertNull(user.getDeactivatedAt());
        assertNull(user.getPasswordChangedAt());
    }

    @Test
    void newAdminShouldSetAdminRole() {
        User admin = User.newAdmin("  Admin  ", "ADMIN@MAIL.COM", "hashed_password");

        assertEquals("Admin", admin.getName());
        assertEquals("admin@mail.com", admin.getEmail());
        assertEquals(Role.ADMIN, admin.getRole());
        assertTrue(admin.getActive());
    }

    @Test
    void deactivateAndActivateShouldUpdateActiveStatusAndDeactivatedAt() {
        User user = User.newCustomer("Ana", "ana@mail.com", "hash");
        Instant now = Instant.parse("2026-11-17T10:00:00Z");

        user.deactivate(now);
        assertFalse(user.getActive());
        assertEquals(now, user.getDeactivatedAt());

        user.activate();
        assertTrue(user.getActive());
        assertNull(user.getDeactivatedAt());
    }

    @Test
    void changePasswordAndRenameShouldUpdateFieldsProperly() {
        User user = User.newCustomer("Ana", "ana@mail.com", "old_hash");
        Instant changedAt = Instant.parse("2026-11-17T12:00:00Z");

        user.changePassword("new_hash", changedAt);
        assertEquals("new_hash", user.getPasswordHash());
        assertEquals(changedAt, user.getPasswordChangedAt());

        user.rename("  Ana Costa  ");
        assertEquals("Ana Costa", user.getName());
    }

    @Test
    void transientUsersWithNullIdShouldNotBeEqual() {
        User user1 = User.newCustomer("Ana", "ana@mail.com", "hash");
        User user2 = User.newCustomer("Ana", "ana@mail.com", "hash");

        assertEquals(user1, user1);
        assertNotEquals(user1, user2);
    }
}
package com.lima.ecommerce.auth.infrastructure.persistence;

import com.lima.ecommerce.auth.domain.entities.User;
import com.lima.ecommerce.auth.domain.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=validate")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindAndCheckExistenceByEmailIgnoringCase() {
        User customer = User.newCustomer("Ana Silva", "ana@mail.com", "hashed_secret");
        userRepository.saveAndFlush(customer);

        Optional<User> found = userRepository.findByEmailIgnoreCase("ANA@MAIL.COM");

        assertTrue(found.isPresent());
        assertEquals(customer.getId(), found.get().getId());
        assertEquals("ana@mail.com", found.get().getEmail());
        assertNotNull(found.get().getCreatedAt());
        assertNotNull(found.get().getUpdatedAt());
        assertEquals(0L, found.get().getVersion());

        assertTrue(userRepository.existsByEmailIgnoreCase("Ana@Mail.Com"));
        assertFalse(userRepository.existsByEmailIgnoreCase("outro@mail.com"));
    }

    @Test
    void shouldCountOnlyActiveUsersByRole() {
        User activeCustomer = User.newCustomer("Cliente Ativo", "c1@mail.com", "hash1");
        User inactiveCustomer = User.newCustomer("Cliente Inativo", "c2@mail.com", "hash2");
        inactiveCustomer.deactivate(Instant.now());

        User activeAdmin = User.newAdmin("Admin Ativo", "admin@mail.com", "hash3");

        userRepository.saveAndFlush(activeCustomer);
        userRepository.saveAndFlush(inactiveCustomer);
        userRepository.saveAndFlush(activeAdmin);

        assertEquals(1L, userRepository.countByRoleAndActiveTrue(Role.CUSTOMER));
        assertEquals(1L, userRepository.countByRoleAndActiveTrue(Role.ADMIN));
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenInsertingDuplicateEmailWithDifferentCase() {
        User first = User.newCustomer("Ana", "ana@mail.com", "hash1");
        userRepository.saveAndFlush(first);

        User duplicate = User.newCustomer("Outra Ana", "ANA@MAIL.COM", "hash2");

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.saveAndFlush(duplicate);
        });
    }
}
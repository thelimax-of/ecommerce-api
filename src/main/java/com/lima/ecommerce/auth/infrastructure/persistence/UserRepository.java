package com.lima.ecommerce.auth.infrastructure.persistence;

import com.lima.ecommerce.auth.domain.entities.User;
import com.lima.ecommerce.auth.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    long countByRoleAndActiveTrue(Role role);
}

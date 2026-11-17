package com.lima.ecommerce.auth.domain.entities;

import com.lima.ecommerce.auth.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "email", nullable = false, length = 254)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "deactivated_at", nullable = true)
    private Instant deactivatedAt;

    @Column(name = "password_changed_at", nullable = true)
    private Instant passwordChangedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    protected User() {}

    public static User newCustomer(String name, String email, String passwordHash) {
        User user = new User();
        user.name = name.strip();
        user.email = normalizeEmail(email);
        user.passwordHash = passwordHash;
        user.role = Role.CUSTOMER;
        user.active = true;

        return user;
    }

    public static User newAdmin(String name, String email, String passwordHash) {
        User user = new User();
        user.name = name.strip();
        user.email = normalizeEmail(email);
        user.passwordHash = passwordHash;
        user.role = Role.ADMIN;
        user.active = true;

        return user;
    }

    @PrePersist
    protected void onCreate() {
        var time =  Instant.now();
        this.createdAt = time;
        this.updatedAt = time;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public void deactivate(Instant now){
        this.active = false;

        this.deactivatedAt = now;
    }

    public void activate(){
        this.active = true;
        this.deactivatedAt = null;
    }

    public void changePassword(String newPassword, Instant now) {
        this.passwordHash = newPassword;
        this.passwordChangedAt = now;
    }

    public void rename(String name) {
        this.name = name.strip();
    }

    private static String normalizeEmail(String email) {
        return email.strip().toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof User other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

CREATE TABLE users
(
    id                  UUID PRIMARY KEY,
    name                VARCHAR(120) NOT NULL,
    email               VARCHAR(254) NOT NULL,
    password_hash       VARCHAR(100) NOT NULL,
    role                VARCHAR(20)  NOT NULL CHECK ( role in ('ADMIN', 'CUSTOMER')),
    active              BOOLEAN      NOT NULL DEFAULT TRUE,
    deactivated_at      TIMESTAMPTZ           DEFAULT NULL,
    password_changed_at TIMESTAMPTZ           DEFAULT NULL,
    created_at          TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version             BIGINT       NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX idx_users_email_unique ON users (LOWER(email));
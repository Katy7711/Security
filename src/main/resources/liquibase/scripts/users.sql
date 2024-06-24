-- liquibase formatted sql

-- changeset denieva:1

CREATE TABLE IF NOT EXISTS users
(
    id        BIGSERIAL PRIMARY KEY,
    email     varchar NOT NULL,
    password  varchar NOT NULL,
    user_name varchar NOT NULL,
    role      varchar NOT NULL
);

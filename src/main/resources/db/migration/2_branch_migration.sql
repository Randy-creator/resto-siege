CREATE TABLE branches(
    branch_id BIGINT primary key,
    branch_name VARCHAR(255) UNIQUE,
    branch_url VARCHAR(255) UNIQUE,
    branch_api_key VARCHAR(255) UNIQUE
);
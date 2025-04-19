CREATE DATABASE siege;

\c siege


CREATE TABLE sale (
    id SERIAL PRIMARY KEY,
    branch_name VARCHAR(50),
    dish_name VARCHAR(255),
    sale_quantity DOUBLE PRECISION,
    total_earned DOUBLE PRECISION,
    CONSTRAINT unique_branch_dish UNIQUE (branch_name, dish_name)
);

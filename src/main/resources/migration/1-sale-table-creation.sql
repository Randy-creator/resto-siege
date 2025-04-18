CREATE DATABASE siege;

\c siege


CREATE TABLE sale (
    id SERIAL PRIMARY KEY,
    dish_name VARCHAR(255),
    sale_quantity DOUBLE PRECISION,
    total_earned DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS Dish(
    dish_id BIGINT primary key,
    dish_name varchar(200) NOT NULL,
    dish_price FLOAT NOT NULL
);
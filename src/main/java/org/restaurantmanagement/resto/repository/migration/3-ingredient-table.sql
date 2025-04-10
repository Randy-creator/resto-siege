create type Unit as ENUM('G', 'L', 'U');


CREATE TABLE IF NOT EXISTS Ingredient(
    ingredient_id BIGINT primary key,
    ingredient_name varchar(200) NOT NULL,
    unit Unit NOT NULL
);
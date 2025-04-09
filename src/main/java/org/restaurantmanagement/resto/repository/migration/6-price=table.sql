CREATE TABLE IF NOT EXISTS Price(
    price_id BIGINT primary key,
    ingredient_id INT REFERENCES Ingredient(ingredient_id),
    amount FLOAT NOT NULL,
    dateTime TIMESTAMP WITH TIME ZONE
);
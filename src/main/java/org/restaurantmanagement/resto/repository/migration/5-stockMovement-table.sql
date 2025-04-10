create type StockMovementTypes as ENUM('IN', 'OUT');

CREATE TABLE StockMovement(
    stock_movement_id BIGINT primary key,
    ingredient_id INT REFERENCES Ingredient(ingredient_id) ON delete CASCADE,
    quantity FLOAT NOT NULL,
    unit Unit,
    stockMovementType StockMovementTypes NOT NULL,
    creationDateTime TIMESTAMP WITH TIME ZONE
);
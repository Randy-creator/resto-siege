create type StockMovementTypes as ENUM('IN', 'OUT');

CREATE TABLE StockMovement(
    stock_movement_id BIGINT primary key,
    quantity FLOAT NOT NULL,
    unit Unit,
    stockMovementType StockMovementTypes NOT NULL,
    creationDateTime TIMESTAMP WITH TIME ZONE
);
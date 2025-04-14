INSERT INTO StockMovement (stock_movement_id, ingredient_id, stockMovementType, quantity, unit, creationDateTime)
VALUES
    (1, 4, 'IN', 100.00, 'U', '2025-02-01 08:00:00'),
    (2, 1, 'IN', 50.00, 'U', '2025-02-01 08:00:00'),
    (3, 2, 'IN', 10000.00, 'G', '2025-02-01 08:00:00'),
    (4, 3, 'IN', 20.00, 'L', '2025-02-01 08:00:00'),
    (5, 4, 'OUT', 10.00, 'U', '2025-02-02 10:00:00'),
    (6, 4, 'OUT', 10.00, 'U', '2025-02-02 15:00:00'),
    (7, 1, 'OUT', 20.00, 'U', '2025-02-05 16:00:00');

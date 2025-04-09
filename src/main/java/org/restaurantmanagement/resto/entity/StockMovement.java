package org.restaurantmanagement.resto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMovement {
    private Long id;
    private Double quantity;
    private Unit unit;
    private StockMovementType stockMovementType;
    private LocalDateTime creationDateTime;
}

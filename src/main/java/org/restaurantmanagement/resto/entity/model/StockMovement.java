package org.restaurantmanagement.resto.entity.model;

import lombok.Getter;
import lombok.Setter;
import org.restaurantmanagement.resto.entity.Enum.MovementType;
import org.restaurantmanagement.resto.entity.Enum.Unit;

import java.time.LocalDateTime;


@Getter
@Setter
public class StockMovement {
    private final long ingredientId;
    private final double movementQuantity;
    private final MovementType movementType;
    private final LocalDateTime movementDate;
    private final Unit unit;

    public StockMovement(long ingredientId, double movementQuantity,  Unit unit,MovementType movementType, LocalDateTime movementDate) {
        this.ingredientId = ingredientId;
        this.movementQuantity = movementQuantity;
        this.movementType = movementType;
        this.movementDate = movementDate;
        this.unit = unit;
    }
}


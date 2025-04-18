package org.restaurantmanagement.resto.entity.DTO.stockMovement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurantmanagement.resto.entity.Enum.MovementType;
import org.restaurantmanagement.resto.entity.Enum.Unit;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockMovementDto {
    private double movementQuantity;
    private MovementType movementType;
    private LocalDateTime movementDate;
    private Unit unit;
}

package org.restaurantmanagement.resto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ingredient {
    private Long id;
    private String name;
    private Double quantity;
    private List<StockMovement> stockMovements;

    public List<StockMovement> addStockMovements(List<StockMovement> stockMovements) {
        if (getStockMovements() == null || getStockMovements().isEmpty()){
            return stockMovements;
        }
        getStockMovements().addAll(stockMovements);
        return getStockMovements();
    }

}

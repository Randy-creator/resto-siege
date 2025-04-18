package org.restaurantmanagement.resto.entity.DTO.ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.restaurantmanagement.resto.entity.model.Price;
import org.restaurantmanagement.resto.entity.model.StockMovement;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ingredient {
    private long id;
    private String name;
    private Double availableQuantity;
    private Double actualPrice;
    private List<StockMovement> stockMovements;
    private List<Price> prices;
}


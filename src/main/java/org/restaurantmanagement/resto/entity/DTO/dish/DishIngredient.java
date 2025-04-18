package org.restaurantmanagement.resto.entity.DTO.dish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.restaurantmanagement.resto.entity.DTO.ingredient.IngredientBasicProperty;
import org.restaurantmanagement.resto.entity.Enum.Unit;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DishIngredient {
    private Double requireQuantity;
    private Unit unit;
    private IngredientBasicProperty ingredient;
    private Double availableQuantity;
}

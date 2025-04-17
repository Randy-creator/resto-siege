package org.restaurantmanagement.resto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private Long id;
    private String name;
    private Double price;
    private List<Ingredient> ingredientList;

    public Dish(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Double getAvailableQuantity() {
        if (ingredientList == null || ingredientList.isEmpty()) return 0.0;

        return this.ingredientList
                .stream()
                .mapToDouble(ingredient -> (Math.round(ingredient.getAvailableQuantityAt() / ingredient.getQuantity())))
                .min()
                .orElse(0);
    }
}

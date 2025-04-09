package org.restaurantmanagement.resto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class Dish {
    private Long id;
    private String name;
    private List<Price> price;
    private List<Ingredient> ingredientList;

    public Dish(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

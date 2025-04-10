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
}

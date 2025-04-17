package org.restaurantmanagement.resto.entity.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurantmanagement.resto.entity.Dish;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DishCommandedMapper {
    private String name;
    private Double price;


    public static DishCommandedMapper toDishCommandedMapper(Dish dish) {
        return new DishCommandedMapper(dish.getName(), dish.getPrice());
    }
}

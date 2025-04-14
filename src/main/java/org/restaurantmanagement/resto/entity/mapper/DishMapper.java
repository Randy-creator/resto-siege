package org.restaurantmanagement.resto.entity.mapper;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.restaurantmanagement.resto.entity.Dish;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DishMapper {
    private Long id;
    private String name;
    private Double price;
    private Double getAvailableQuantity;


    public DishMapper toDishMapper(Dish dish) {
        return new DishMapper(dish.getId(), dish.getName(), dish.getPrice(), dish.getAvailableQuantity());
    }
}

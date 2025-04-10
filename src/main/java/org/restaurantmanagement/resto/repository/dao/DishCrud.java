package org.restaurantmanagement.resto.repository.dao;

import org.restaurantmanagement.resto.entity.Dish;
import org.restaurantmanagement.resto.entity.Ingredient;

import java.util.List;

public interface DishCrud {
    public List<Dish> getAllDish(int page, int pageSize);

    public Dish getDishById(Long id);

    public Dish createDish(Dish dish);

    public List<Ingredient> save(Long id, List<Ingredient> ingredientListToSave);
}

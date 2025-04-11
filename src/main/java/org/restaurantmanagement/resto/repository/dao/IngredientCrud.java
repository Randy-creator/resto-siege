package org.restaurantmanagement.resto.repository.dao;

import org.restaurantmanagement.resto.entity.Ingredient;

import java.util.List;

public interface IngredientCrud {
    public List<Ingredient> getAllIngredients(int page, int size);

    public Ingredient getIngredientById(Long id);
}

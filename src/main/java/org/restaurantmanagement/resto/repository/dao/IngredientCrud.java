package org.restaurantmanagement.resto.repository.dao;

import org.restaurantmanagement.resto.entity.Ingredient;
import org.restaurantmanagement.resto.entity.Price;
import org.restaurantmanagement.resto.entity.StockMovement;

import java.util.List;

public interface IngredientCrud {
    public List<Ingredient> getAllIngredients(int page, int size);

    public Ingredient getIngredientById(Long id);

    public Ingredient createIngredient(Ingredient ingredient);

    public Ingredient save(Long id, List<StockMovement> stockMovementList, List<Price> priceList);

    public Ingredient savePrices(Long id, List<Price> priceList);


    public Ingredient saveStockMovements(Long id, List<StockMovement> stockMovementList);
}


package org.restaurantmanagement.resto.service;

import org.restaurantmanagement.resto.entity.Ingredient;
import org.restaurantmanagement.resto.entity.Price;
import org.restaurantmanagement.resto.entity.StockMovement;
import org.restaurantmanagement.resto.repository.dao.DishCrudImpl;
import org.restaurantmanagement.resto.repository.dao.IngredientCrudImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientCrudImpl ingredientDao;

    @Autowired
    public IngredientService(IngredientCrudImpl ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public List<Ingredient> getAll(int page, int size) {
        return ingredientDao.getAllIngredients(page, size);
    }

    public Ingredient getById(Long id) {
        return ingredientDao.getIngredientById(id);
    }

    public Ingredient save(Long id, List<StockMovement> stockMovementList, List<Price> priceList) {
        return ingredientDao.save(id, stockMovementList, priceList);
    }

    public Ingredient savePrices(Long id, List<Price> priceList) {
        return ingredientDao.savePrices(id, priceList);
    }

    public Ingredient saveStockMovement(Long id, List<StockMovement> stockMovementList) {
        return ingredientDao.saveStockMovements(id, stockMovementList);
    }
}

package org.restaurantmanagement.resto.mapper;

import org.restaurantmanagement.resto.entity.DTO.ingredient.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {
    public Ingredient ingredientToIngredientDto(org.restaurantmanagement.resto.entity.model.Ingredient ingredient){
        Ingredient ingredientDto = new Ingredient();
        ingredientDto.setId(ingredient.getIngredientId());
        ingredientDto.setName(ingredient.getIngredientName());
        ingredientDto.setPrices(ingredient.getUnitPrice());
        ingredientDto.setActualPrice(ingredient.getNearestPrice().getValue());
        ingredientDto.setAvailableQuantity(ingredient.getAvailableQuantity());
        ingredientDto.setStockMovements(ingredient.getStockMovements());
        return ingredientDto;
    }

}

package org.restaurantmanagement.resto.controller;

import org.restaurantmanagement.resto.entity.Ingredient;
import org.restaurantmanagement.resto.entity.Price;
import org.restaurantmanagement.resto.entity.StockMovement;
import org.restaurantmanagement.resto.service.DishService;
import org.restaurantmanagement.resto.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IngredientController {
    @Autowired
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getAll(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(ingredientService.getAll(page, size));
    }

    @GetMapping("/ingredient/{id}")
    public ResponseEntity<Ingredient> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.getById(id));
    }

    @PutMapping("/ingredient/{id}/save")
    public ResponseEntity<Ingredient> save(@PathVariable Long id,
                                           @RequestBody List<StockMovement> stockMovementList,
                                           @RequestBody List<Price> priceList) {
        return ResponseEntity.ok(ingredientService.save(id, stockMovementList, priceList));
    }

    @PutMapping("/ingredient/{id}/prices")
    public ResponseEntity<Ingredient> savePrices(@PathVariable Long id,
                                                 @RequestBody List<Price> priceList) {
        return ResponseEntity.ok(ingredientService.savePrices(id, priceList));
    }

    @PutMapping("/ingredient/{id}/stockMovement")
    public ResponseEntity<Ingredient> saveStockMovement(@PathVariable Long id,
                                                        @RequestBody List<StockMovement> stockMovements) {
        return ResponseEntity.ok(ingredientService.saveStockMovement(id, stockMovements));
    }
}

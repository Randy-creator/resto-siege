package org.restaurantmanagement.resto.controller;

import org.restaurantmanagement.resto.entity.Dish;
import org.restaurantmanagement.resto.entity.Ingredient;
import org.restaurantmanagement.resto.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DishController {
    @Autowired
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public ResponseEntity<List<Dish>> getAllDishes(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(dishService.getAllDishes(page, size));
    }

    @GetMapping("/dish")
    public ResponseEntity<Dish> getDishById(@RequestParam Long id) {
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    @PutMapping
    public ResponseEntity<Dish> saveDish(@PathVariable Long id, @RequestBody List<Ingredient> ingredientList) {
        return ResponseEntity.ok(dishService.saveDish(id, ingredientList));
    }
}

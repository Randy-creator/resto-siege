package org.restaurantmanagement.resto.controller;

import org.restaurantmanagement.resto.entity.Dish;
import org.restaurantmanagement.resto.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DishController {
    @Autowired
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public ResponseEntity<List<Dish>> getAllDishes(@RequestParam int page,
                                                   @RequestParam int size) {
        return ResponseEntity.ok(dishService.getAllDishes(page, size));
    }
}

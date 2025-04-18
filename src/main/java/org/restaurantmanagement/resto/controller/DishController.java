package org.restaurantmanagement.resto.controller;

import org.restaurantmanagement.resto.entity.Enum.Mode;
import org.restaurantmanagement.resto.service.dish.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RestController
public class DishController {
    @Autowired
    private DishServiceImpl dishService;

    @GetMapping("/dishes/{dishId}/processingTime")
    public ResponseEntity<Object> getProcessingTime(
            @PathVariable("dishId") long dishId,
            @RequestParam(defaultValue = "SECONDS", required = false) TimeUnit durationType,
            @RequestParam(defaultValue = "AVG", required = false) Mode mode,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        try {
            double processingTime = dishService.calculateProcessingTimeForDishOrders(
                    dishId,
                    start,
                    end,
                    durationType,
                    mode
            );
            return ResponseEntity.ok(dishId + "processing time : " + processingTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

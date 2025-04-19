package org.restaurantmanagement.resto.controller;

import org.restaurantmanagement.resto.entity.model.Sale;
import org.restaurantmanagement.resto.service.Sale.SaleServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SaleController {
    private final SaleServiceImpl saleService;

    public SaleController(SaleServiceImpl saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/sales")
    public ResponseEntity<Object> postSales(@RequestParam String startTime, @RequestParam String endTime) {
        try {
            saleService.getSales(startTime, endTime);
            return ResponseEntity.ok("Sales successfully fetched and migrated.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Migration failed: " + e.getMessage());
        }
    }

    @GetMapping("/bestSales")
    public ResponseEntity<List<Sale>> getBestSales(@RequestParam(defaultValue = "3") int top) {
        try {
            return ResponseEntity.ok().body(saleService.getBestSale(top));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

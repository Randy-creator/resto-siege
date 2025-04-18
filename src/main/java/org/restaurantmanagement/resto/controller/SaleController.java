package org.restaurantmanagement.resto.controller;

import org.restaurantmanagement.resto.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/sales")
    public ResponseEntity<Object> postBestSales(@RequestParam String startTime, @RequestParam String endTime) {
        try {
            saleService.getBestSales(startTime, endTime);
            return ResponseEntity.ok("Sales successfully fetched and migrated.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Migration failed: " + e.getMessage());
        }
    }
}

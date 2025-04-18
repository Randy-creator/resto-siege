package org.restaurantmanagement.resto.controller;

import org.restaurantmanagement.resto.entity.Sale;
import org.restaurantmanagement.resto.service.Dashboard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DashBoardController {
    private final Dashboard dashboard;

    public DashBoardController(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    @GetMapping("/bestSales ")
    public ResponseEntity<Object> getBestSales(@RequestParam LocalDateTime start,
                                               @RequestParam LocalDateTime end,
                                               @RequestParam int size

    ) {
        try {
            List<Sale> dishSales = dashboard.getBestSales(start, end);
            List<Sale> body = new ArrayList<>();
            while (body.size() != size) {
                body.add(dishSales.removeFirst());
            }
            return ResponseEntity.ok().body(body);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

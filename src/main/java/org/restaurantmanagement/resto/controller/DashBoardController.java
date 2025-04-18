package org.restaurantmanagement.resto.controller;

import org.restaurantmanagement.resto.entity.DurationType;
import org.restaurantmanagement.resto.entity.ProcessingTime;
import org.restaurantmanagement.resto.entity.ProcessingTimeType;
import org.restaurantmanagement.resto.entity.Sale;
import org.restaurantmanagement.resto.service.Dashboard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/dishes/{id}/processingTime")
    public ResponseEntity<Object> getProcessingTime(
            @PathVariable Long id,
            @RequestParam(defaultValue = "SECOND", required = false) String durationType,
            @RequestParam(defaultValue = "AVERAGE", required = false) String processingTimeType) {

        try {
            ProcessingTime processingTime =
                    dashboard.getProcessingTimeFor(
                            id,
                            ProcessingTimeType.valueOf(processingTimeType),
                            DurationType.valueOf(durationType));
            return ResponseEntity.ok().body(processingTime);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }
}

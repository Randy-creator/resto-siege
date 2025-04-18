package org.restaurantmanagement.resto.controller;

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

    @GetMapping("/bestSales ")
    public ResponseEntity<Object> getBestSales(@RequestParam LocalDateTime start,
                                               @RequestParam LocalDateTime end,
                                               @RequestParam int size

    ) {
        throw new RuntimeException("not yet");
    }

    @GetMapping("/dishes/{id}/processingTime")
    public ResponseEntity<Object> getProcessingTime(
            @PathVariable Long id,
            @RequestParam(defaultValue = "SECOND", required = false) String durationType,
            @RequestParam(defaultValue = "AVERAGE", required = false) String processingTimeType) {

        throw new RuntimeException("not yet");

    }
}

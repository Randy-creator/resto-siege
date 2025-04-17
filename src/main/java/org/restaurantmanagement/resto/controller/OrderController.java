package org.restaurantmanagement.resto.controller;

import org.restaurantmanagement.resto.entity.Order;
import org.restaurantmanagement.resto.entity.mapper.OrderMapper;
import org.restaurantmanagement.resto.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/{reference}")
    public ResponseEntity<Optional<OrderMapper>> getByReference(@PathVariable String reference) {
        return ResponseEntity.ok(orderService.getByReference(reference));
    }
}

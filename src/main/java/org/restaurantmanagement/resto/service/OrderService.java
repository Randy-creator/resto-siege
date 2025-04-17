package org.restaurantmanagement.resto.service;

import org.restaurantmanagement.resto.entity.DishOrder;
import org.restaurantmanagement.resto.entity.Order;
import org.restaurantmanagement.resto.entity.mapper.DishCommandedMapper;
import org.restaurantmanagement.resto.entity.mapper.DishMapper;
import org.restaurantmanagement.resto.entity.mapper.OrderMapper;
import org.restaurantmanagement.resto.repository.dao.OrderCrudImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final OrderCrudImpl orderDao;

    public OrderService(OrderCrudImpl orderDao) {
        this.orderDao = orderDao;
    }

    public Optional<OrderMapper> getByReference(String reference) {
        return orderDao.getByReference(reference).map(OrderMapper::toOrderMapper);
    }
}

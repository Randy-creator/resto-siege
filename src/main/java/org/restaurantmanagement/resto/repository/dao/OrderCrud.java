package org.restaurantmanagement.resto.repository.dao;

import org.restaurantmanagement.resto.entity.Order;
import org.restaurantmanagement.resto.entity.StatusType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OrderCrud {
    List<Order> getAll(int pageSize, int pageNumber);

    Optional<Order> getByReference(String reference);

    Order getByID(long orderId);

    void createOrder(Order order) throws SQLException;

    void save(Order order) throws SQLException;

    void updateStatus(StatusType statusType, long orderId);
}

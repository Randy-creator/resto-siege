package org.restaurantmanagement.resto.repository.dao;

import org.restaurantmanagement.resto.entity.Status;
import org.restaurantmanagement.resto.entity.StatusType;

import java.util.List;

public interface StatusCrud {
    List<Status> getStatusForOrder(long orderId);

    List<Status> getStatusForDishOrder(long dishOrderId);

    void insertStatusForOrder(long orderId, StatusType status);

    void insertStatusForDishOrder(long dishOrderId, StatusType statusType);
}

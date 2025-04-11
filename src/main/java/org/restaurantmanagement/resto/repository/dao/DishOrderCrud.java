package org.restaurantmanagement.resto.repository.dao;

import org.restaurantmanagement.resto.entity.DishOrder;
import org.restaurantmanagement.resto.entity.StatusType;

import java.util.List;

public interface DishOrderCrud {

    void updateDishOrderStatus(StatusType statusType, long orderId);

    void addDishOrder(DishOrder dishOrder, long orderId);

    List<DishOrder> getDishOrdersByOrderId(long orderId);

    void deleteDishOrder(long dishOrderId);
}

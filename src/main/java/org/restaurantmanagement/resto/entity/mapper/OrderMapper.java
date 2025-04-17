package org.restaurantmanagement.resto.entity.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.restaurantmanagement.resto.entity.DishOrder;
import org.restaurantmanagement.resto.entity.Order;
import org.restaurantmanagement.resto.entity.Status;

import java.util.List;

@AllArgsConstructor
@Data
public class OrderMapper {
    private final long orderID;
    private final String reference;
    private List<DishOrderMapper> orderedDish;

    public static OrderMapper toOrderMapper(Order order) {
        return new OrderMapper(
                order.getOrderID(),
                order.getReference(),
                order.getOrderedDish().stream().map(DishOrderMapper::toDishOrderMapper).toList()
        );
    }
}

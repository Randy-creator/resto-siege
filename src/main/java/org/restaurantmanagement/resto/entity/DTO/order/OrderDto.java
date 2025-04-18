package org.restaurantmanagement.resto.entity.DTO.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.restaurantmanagement.resto.entity.DTO.dish.OrderDish;
import org.restaurantmanagement.resto.entity.Enum.StatusType;

import java.util.List;


@Data
@AllArgsConstructor
public class OrderDto {
    private final String reference;
    private final StatusType Status;
    private final List<OrderDish> orderDish;
}

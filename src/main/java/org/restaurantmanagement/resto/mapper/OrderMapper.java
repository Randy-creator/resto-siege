package org.restaurantmanagement.resto.mapper;

import org.restaurantmanagement.resto.entity.DTO.order.OrderDto;
import org.restaurantmanagement.resto.entity.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    DishMapper dishMapper;
    public OrderMapper(DishMapper dishMapper){
        this.dishMapper = dishMapper;
    }
    public OrderDto mapToDto(Order order){
        return new OrderDto(
                order.getReference(),
                order.getActualStatus(),
                order.getOrderedDish().stream().map(dishOrder -> dishMapper.mapToOrderDish(dishOrder)).toList()
        );
    }
}

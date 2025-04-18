package org.restaurantmanagement.resto.entity.DTO.dish;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurantmanagement.resto.entity.Enum.StatusType;

@Data
@NoArgsConstructor
public class OrderDish {
    private long dishId;
    private String name;
    private Integer quantity;
    private StatusType status;

    public OrderDish(long dishId, Integer quantity) {
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public OrderDish(long dishId, String name, Integer quantity, StatusType status) {
        this.dishId = dishId;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }
}

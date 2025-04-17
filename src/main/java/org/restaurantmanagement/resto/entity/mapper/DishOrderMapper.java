package org.restaurantmanagement.resto.entity.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurantmanagement.resto.entity.Dish;
import org.restaurantmanagement.resto.entity.DishOrder;
import org.restaurantmanagement.resto.entity.Status;
import org.restaurantmanagement.resto.entity.StatusType;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Data
public class DishOrderMapper {
    private final long dishOrderId;
    private DishCommandedMapper commendedDish;
    private final Integer dishQuantityCommanded;
    private List<Status> statusList;

    public static DishOrderMapper toDishOrderMapper(DishOrder dishOrder) {
        return new DishOrderMapper(
                dishOrder.getDishOrderId(),
                DishCommandedMapper.toDishCommandedMapper(dishOrder.getCommendedDish()), // This line correctly maps Dish to DishCommandedMapper
                dishOrder.getDishQuantityCommanded(),
                dishOrder.getStatusList()
        );
    }
}




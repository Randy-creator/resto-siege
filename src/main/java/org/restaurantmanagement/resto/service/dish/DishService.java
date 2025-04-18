package org.restaurantmanagement.resto.service.dish;

import org.restaurantmanagement.resto.entity.Enum.Mode;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public interface DishService {
    double calculateProcessingTimeForDishOrders(long dishId, LocalDateTime start, LocalDateTime end, TimeUnit unit, Mode mode);
}

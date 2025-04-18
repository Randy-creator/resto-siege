package org.restaurantmanagement.resto.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurantmanagement.resto.entity.Enum.Mode;
import org.restaurantmanagement.resto.entity.Enum.StatusType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DishOrder {
    private long dishOrderId;
    private Dish commendedDish;
    private Integer dishQuantityCommanded;
    private List<Status> statusList;

    public DishOrder(long dishOrderId, Dish commendedDish, Integer dishQuantityCommanded) {
        this.dishOrderId = dishOrderId;
        this.commendedDish = commendedDish;
        this.dishQuantityCommanded = dishQuantityCommanded;
    }

    public StatusType getActualStatus(){
        return statusList
                .stream()
                .max(Comparator.comparing(Status::getCreationDate))
                .map(Status::getStatusType)
                .orElse(null);
    }
}

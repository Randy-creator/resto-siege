package org.restaurantmanagement.resto.service;

import org.restaurantmanagement.resto.entity.*;
import org.restaurantmanagement.resto.repository.dao.DishCrudImpl;
import org.restaurantmanagement.resto.repository.dao.DishOrderCrupImpl;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class Dashboard {
    private final DishOrderCrupImpl dishOrderCrud;

    public Dashboard(DishOrderCrupImpl dishOrderCrud) {
        this.dishOrderCrud = dishOrderCrud;
    }

    public List<Sale> getBestSales(LocalDateTime start, LocalDateTime end) {
        List<DishOrder> allDishOrder = dishOrderCrud.getAllDishOrders(1, 200);

        List<DishOrder> filterByStatus = allDishOrder.stream()
                .filter(dishOrder -> dishOrder.getStatusList().stream().anyMatch(
                        status -> status.getStatusType().equals(StatusType.FINISHED)
                )).toList();

        List<DishOrder> ordersInDateRange = filterByStatus.stream()
                .filter(order -> order.getStatusList().stream()
                        .anyMatch(status -> {
                            LocalDateTime created = LocalDateTime.from(status.getCreationDate());
                            return created.isAfter(start) && created.isBefore(end);
                        }))
                .toList();

        List<Sale> dishSales = new ArrayList<>();

        ordersInDateRange.forEach(dishOrder -> {
            if (dishSales.stream()
                    .anyMatch(
                            sale -> sale.getDishName().equals(dishOrder.getCommendedDish().getName()))) {
                for (Sale sale : dishSales) {
                    Double profit = sale.getProfit();
                    sale.setProfit(profit + (dishOrder.getDishQuantityCommanded() * dishOrder.getCommendedDish().getPrice()));
                    Double quantity = sale.getSoldQuantity();
                    sale.setSoldQuantity(quantity + dishOrder.getDishQuantityCommanded());
                }
            } else {
                Sale saleDish = new Sale();

                saleDish.setDishName(dishOrder.getCommendedDish().getName());
                saleDish.setProfit(dishOrder.getDishQuantityCommanded() * dishOrder.getCommendedDish().getPrice());
                saleDish.setSoldQuantity(Double.valueOf(dishOrder.getDishQuantityCommanded()));

                dishSales.add(saleDish);
            }
        });

        return dishSales.stream().sorted(Comparator.comparing(Sale::getSoldQuantity, Comparator.naturalOrder())).toList();
    }

    public ProcessingTime getProcessingTimeFor(
            Long dishOrderId, ProcessingTimeType processingTimeType, DurationType durationType
    ) {
        List<DishOrder> dishOrders = dishOrderCrud.getDishOrdersByOrderId(dishOrderId);
        List<Duration> durations = dishOrders.stream().map(dishOrder -> {
            LocalDateTime inProgress =
                    LocalDateTime.from(dishOrder.getStatusList().stream()
                            .filter(status -> status.getStatusType().equals(StatusType.IN_PROGRESS))
                            .toList()
                            .getFirst()
                            .getCreationDate());

            LocalDateTime finished =
                    LocalDateTime.from(dishOrder.getStatusList().stream()
                            .filter(status -> status.getStatusType().equals(StatusType.FINISHED))
                            .toList()
                            .getFirst()
                            .getCreationDate());

            return Duration.between(inProgress, finished);
        }).toList();


        ProcessingTime result = new ProcessingTime();
        Duration duration = null;
        result.setProcessingTimeType(processingTimeType);
        result.setDurationType(durationType);
        switch (processingTimeType) {
            case MINIMUM -> {
                duration = durations.stream().min(Duration::compareTo).get();
            }

            case MAXIMUM -> {
                duration = durations.stream().max(Duration::compareTo).get();
            }

            case AVERAGE -> {
                duration = durations.stream().reduce((Duration::plus)).get().dividedBy(durations.size());
            }
        }

        switch (durationType) {
            case SECOND -> result.setValue(duration.toSeconds());
            case MINUTE -> result.setValue(duration.toMinutes());
            case HOUR -> result.setValue(duration.toHours());
        }

        return result;
    }
}

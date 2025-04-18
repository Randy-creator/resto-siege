package org.restaurantmanagement.resto.service;

import org.restaurantmanagement.resto.entity.*;
import org.restaurantmanagement.resto.repository.dao.DishCrudImpl;
import org.restaurantmanagement.resto.repository.dao.DishOrderCrupImpl;
import org.springframework.stereotype.Service;

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
                            sale -> sale.getDishName().equals(dishOrder.getCommendedDish().getName()))){
                for (Sale sale : dishSales) {
                    Double profit = sale.getProfit();
                    sale.setProfit(profit + (dishOrder.getDishQuantityCommanded() * dishOrder.getCommendedDish().getPrice()));
                    Double quantity = sale.getSoldQuantity();
                    sale.setSoldQuantity(quantity + dishOrder.getDishQuantityCommanded());
                }
            }
            else {
                Sale saleDish = new Sale();

                saleDish.setDishName(dishOrder.getCommendedDish().getName());
                saleDish.setProfit(dishOrder.getDishQuantityCommanded() * dishOrder.getCommendedDish().getPrice());
                saleDish.setSoldQuantity(Double.valueOf(dishOrder.getDishQuantityCommanded()));

                dishSales.add(saleDish);
            }
        });

        return dishSales.stream().sorted(Comparator.comparing(Sale::getSoldQuantity, Comparator.naturalOrder())).toList();
    }
}

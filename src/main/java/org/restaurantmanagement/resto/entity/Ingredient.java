package org.restaurantmanagement.resto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.restaurantmanagement.resto.entity.StockMovementType.IN;
import static org.restaurantmanagement.resto.entity.StockMovementType.OUT;

@NoArgsConstructor
@Data
public class Ingredient {
    private Long id;
    private String name;
    private Double quantity;
    private Unit unit;
    private List<Price> prices;
    private List<StockMovement> stockMovements;

    public Ingredient(Long id, String name, Double quantity, Unit unit) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Ingredient(Long id, String name, Double quantity, Unit unit, List<Price> prices, List<StockMovement> stockMovements) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.prices = prices;
        this.stockMovements = stockMovements;
    }

    public List<StockMovement> addStockMovements(List<StockMovement> stockMovements) {
        if (getStockMovements() == null || getStockMovements().isEmpty()) {
            return stockMovements;
        }
        getStockMovements().addAll(stockMovements);
        return getStockMovements();
    }

    public List<Price> addPrices(List<Price> prices) {
        if (getPrices() == null || getPrices().isEmpty()) {
            return prices;
        }
        getPrices().addAll(prices);
        return getPrices();
    }

    public Double getPriceAt(LocalDateTime dateTime) {
        return findPriceAt(dateTime).orElse(new Price(0.0)).getAmount();
    }

    private Optional<Price> findActualPrice() {
        return prices.stream().max(Comparator.comparing(Price::getDateTime));
    }

    private Optional<Price> findPriceAt(LocalDateTime dateValue) {
        return prices.stream()
                .filter(price -> price.getDateTime().isBefore(dateValue) || price.getDateTime().equals(dateValue))
                .findFirst();
    }

    private Optional<Price> findPriceAt() {
        return findPriceAt(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
    }


    public Double getAvailableQuantityAt() {
        return getAvailableQuantityAt(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
    }


    public Double getAvailableQuantityAt(LocalDateTime datetime) {
        if (stockMovements == null || stockMovements.isEmpty()) {
            return 0.0;
        }

        List<StockMovement> stockMovementsBeforeToday = stockMovements.stream()
                .filter(stockMovement ->
                        stockMovement.getCreationDateTime().isBefore(datetime)
                                || stockMovement.getCreationDateTime().equals(datetime))
                .toList();

        double quantity = 0;
        for (StockMovement stockMovement : stockMovementsBeforeToday) {
            if (IN.equals(stockMovement.getStockMovementType())) {
                quantity += stockMovement.getQuantity();
            } else if (OUT.equals(stockMovement.getStockMovementType())) {
                quantity -= stockMovement.getQuantity();
            }
        }
        return quantity;
    }

}

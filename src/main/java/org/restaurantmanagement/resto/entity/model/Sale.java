package org.restaurantmanagement.resto.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class Sale {
    private String dishName;
    private Double saleQuantity;
    private Double totalEarned;
}

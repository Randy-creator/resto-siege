package org.restaurantmanagement.resto.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Sale {
    private String dishName;
    private Double soldQuantity;
    private Double profit;
}

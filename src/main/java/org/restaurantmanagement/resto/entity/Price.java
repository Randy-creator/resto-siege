package org.restaurantmanagement.resto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private Long id;
    private Double amount;
    private LocalDateTime dateTime;

    public Price(Double amount) {
        this.amount = amount;
        this.dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    }

    public Price(Double amount, LocalDateTime dateTime) {
        this.amount = amount;
        this.dateTime = dateTime;
    }
}

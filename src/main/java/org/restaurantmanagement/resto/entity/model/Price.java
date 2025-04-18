package org.restaurantmanagement.resto.entity.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Price {
    private LocalDateTime modificationDate;
    private Double value;

    public Price(LocalDateTime modificationDate, Double value) {
        this.modificationDate = modificationDate;
        this.value = value;
    }
}

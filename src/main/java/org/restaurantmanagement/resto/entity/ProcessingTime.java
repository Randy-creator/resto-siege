package org.restaurantmanagement.resto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessingTime {
    private ProcessingTimeType processingTimeType;
    private DurationType durationType;
    private Long value;
}

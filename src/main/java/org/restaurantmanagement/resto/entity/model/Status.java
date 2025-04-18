package org.restaurantmanagement.resto.entity.model;

import lombok.Data;
import org.restaurantmanagement.resto.entity.Enum.StatusType;

import java.time.Instant;

@Data
public class Status {
    private final StatusType statusType;
    private final Instant creationDate;

    public Status(StatusType statusType, Instant creationDate){
        this.statusType = statusType;
        this.creationDate = creationDate;
    }
}

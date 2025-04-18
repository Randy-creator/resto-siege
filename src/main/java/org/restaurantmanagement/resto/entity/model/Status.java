package org.restaurantmanagement.resto.entity.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.restaurantmanagement.resto.entity.Enum.StatusType;

import java.time.Instant;

@Data
@NoArgsConstructor
public class Status {
    private StatusType statusType;
    private Instant creationDate;

    public Status(StatusType statusType, Instant creationDate){
        this.statusType = statusType;
        this.creationDate = creationDate;
    }
}

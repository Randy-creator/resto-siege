package org.restaurantmanagement.resto.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Branch {
    private Long branchId;
    private String branchName;
    private String branchUrl;
    private String branchApiKey;
}


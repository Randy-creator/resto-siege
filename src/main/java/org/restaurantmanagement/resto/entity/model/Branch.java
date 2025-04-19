package org.restaurantmanagement.resto.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Branch {
    private Long branch_id;
    private String branch_name;
    private String branch_url;
    private String branch_api_key;
}


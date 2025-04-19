package org.restaurantmanagement.resto.service.Sale;

import org.restaurantmanagement.resto.entity.model.Sale;

import java.util.List;

public interface SaleService {
    void getSales(String startTime, String endTime);

    public List<Sale> getBestSale(int top);
}

package org.restaurantmanagement.resto.repository;

import org.restaurantmanagement.resto.entity.model.Sale;

import java.util.List;

public interface SaleCrud {
    public void saveAll(List<Sale> sales, String branchName);
}

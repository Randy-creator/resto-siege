package org.restaurantmanagement.resto.service;

import org.restaurantmanagement.resto.entity.Dish;
import org.restaurantmanagement.resto.repository.dao.DishCrudImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    private final DishCrudImpl dishDao;


    @Autowired
    public DishService(DishCrudImpl dishDao) {
        this.dishDao = dishDao;
    }

    public List<Dish> getAllDishes(int page, int size) {
        return dishDao.getAllDish(page, size);
    }

    public Dish getDishById(Long id) {
        return dishDao.getDishById(id);
    }
}

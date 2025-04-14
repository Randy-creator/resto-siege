package org.restaurantmanagement.resto.service;

import org.restaurantmanagement.resto.entity.Dish;
import org.restaurantmanagement.resto.entity.Ingredient;
import org.restaurantmanagement.resto.entity.mapper.DishMapper;
import org.restaurantmanagement.resto.repository.dao.DishCrudImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishService {
    private final DishCrudImpl dishDao;


    @Autowired
    public DishService(DishCrudImpl dishDao) {
        this.dishDao = dishDao;
    }

    public List<DishMapper> getAllDishes(int page, int size) {
        DishMapper dishMapper = new DishMapper();
        return dishDao.getAllDish(page, size).stream().map(dishMapper::toDishMapper).collect(Collectors.toList());
    }

    public Dish getDishById(Long id) {
        return dishDao.getDishById(id);
    }

    public Dish saveDish(Long id, List<Ingredient> ingredientList) {
        return dishDao.save(id, ingredientList);
    }
}

package org.restaurantmanagement.resto.repository.dao;

import org.restaurantmanagement.resto.entity.Dish;
import org.restaurantmanagement.resto.entity.Ingredient;
import org.restaurantmanagement.resto.entity.Unit;
import org.restaurantmanagement.resto.repository.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishCrudImpl implements DishCrud {

    private final DbConnection db;

    @Autowired
    public DishCrudImpl(DbConnection db) {
        this.db = db;
    }


    private List<Ingredient> getIngredientOfDish(Long dishId) {
        String sql = """
                SELECT i.ingredient_id, i.ingredient_name, i.quantity, i.unit
                FROM DishIngredient di JOIN Ingredient i ON di.ingredient_id=i.ingredient_id
                JOIN Dish d ON di.dish_id=d.dish_id WHERE d.dish_id = ?
                """;

        List<Ingredient> ingredientList = new ArrayList<>();
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, dishId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ingredientList.add(new Ingredient(
                            rs.getLong("ingredient_id"),
                            rs.getString("ingredient_name"),
                            rs.getDouble("quantity"),
                            Unit.valueOf(rs.getString(("unit")))
                    ));
                }
            }
            return ingredientList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dish> getAllDish(int page, int pageSize) {
        String sql = """
                SELECT dish_id, dish_name, dish_price FROM Dish LIMIT ? OFFSET ? 
                """;

        List<Dish> dishList = new ArrayList<>();

        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dishList.add(new Dish(
                            rs.getLong("dish_id"),
                            rs.getString("dish_name"),
                            rs.getDouble("dish_price"),
                            getIngredientOfDish(rs.getLong("dish_id"))
                    ));
                }
            }

            return dishList;

        } catch (
                Exception e) {
            throw new RuntimeException(e);
        }
    }
}

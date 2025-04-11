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
                SELECT i.ingredient_id, i.ingredient_name, di.quantity, i.unit
                FROM DishIngredient di JOIN Ingredient i ON di.ingredient_id=i.ingredient_id
                JOIN Dish d ON di.dish_id=d.dish_id WHERE d.dish_id = ?
                """;

        List<Ingredient> ingredientList = new ArrayList<>();
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, dishId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ingredientList.add(new Ingredient(rs.getLong("ingredient_id"), rs.getString("ingredient_name"), rs.getDouble("quantity"), Unit.valueOf(rs.getString(("unit")))));
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
                    dishList.add(new Dish(rs.getLong("dish_id"), rs.getString("dish_name"), rs.getDouble("dish_price"), getIngredientOfDish(rs.getLong("dish_id"))));
                }
            }

            return dishList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dish getDishById(Long id) {
        String sql = """
                SELECT dish_id, dish_name, dish_price FROM Dish WHERE dish_id = ? 
                """;
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Dish(rs.getLong("dish_id"), rs.getString("dish_name"), rs.getDouble("dish_price"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Dish createDish(Dish dish) {
        String sql = """
                INSERT INTO Dish (dish_id, dish_name, dish_price) VALUES (?, ?, ?)
                ON CONFLICT(dish_id) DO UPDATE
                                        SET dish_name=EXCLUDED.dish_name, dish_price=EXCLUDED.dish_price
                """;
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, dish.getId());
            ps.setString(2, dish.getName());
            ps.setDouble(3, dish.getPrice());

            ps.executeUpdate();

            return dish;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ingredient> save(Long id, List<Ingredient> ingredientListToSave) {
        Dish dishToSave = getDishById(id);
        for (Ingredient ingredient : dishToSave.getIngredientList()) {
            String sql = """
                    INSERT INTO DishIngredient (dish_id, ingredient_id, quantity)
                    VALUES (?, ?, ?)
                    ON CONFLICT DO UPDATE
                    dish_id=EXCLUDED.dish_id,
                    ingredient_id=EXCLUDED.ingredient_id,
                    quantity=EXCLUDED.quantity 
                    """;
            try (Connection connection = db.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, dishToSave.getId());
                ps.setLong(2, ingredient.getId());
                ps.setDouble(3, ingredient.getQuantity());

                ps.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ingredientListToSave;
    }
}

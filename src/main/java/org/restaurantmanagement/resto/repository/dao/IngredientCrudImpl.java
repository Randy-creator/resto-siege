package org.restaurantmanagement.resto.repository.dao;

import org.restaurantmanagement.resto.entity.*;
import org.restaurantmanagement.resto.repository.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientCrudImpl implements IngredientCrud {
    private final DbConnection db;

    @Autowired
    public IngredientCrudImpl(DbConnection db) {
        this.db = db;
    }

    private List<Price> getPriceListOfIngredient(Long id) {
        String sql = """
                SELECT p.price_id, p.amount, p.dateTime FROM Price p 
                             WHERE p.ingredient_id = ?  
                """;
        List<Price> priceList = new ArrayList<>();
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    priceList.add(new Price(
                            rs.getLong("price_id"),
                            rs.getDouble("amount"),
                            rs.getTimestamp("dateTime").toLocalDateTime()
                    ));
                }
            }
            return priceList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<StockMovement> getStockMovementListOfIngredient(Long id) {
        String sql = """
                SELECT s.stock_movement_id, s.quantity, s.unit, s.stockMovementType, s.creationDateTime
                FROM StockMovement s 
                WHERE s.ingredient_id = ? 
                """;
        List<StockMovement> stockMovementList = new ArrayList<>();
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    stockMovementList.add(new StockMovement(
                            rs.getLong("stock_movement_id"),
                            rs.getDouble("quantity"),
                            Unit.valueOf(rs.getString("unit")),
                            StockMovementType.valueOf(rs.getString("stockMovementType")),
                            rs.getTimestamp("creationDateTime").toLocalDateTime()
                    ));
                }
            }
            return stockMovementList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ingredient> getAllIngredients(int page, int size) {
        String sql = """
                SELECT i.ingredient_id, i.ingredient_name, di.quantity, i.unit
                FROM DishIngredient di JOIN Ingredient i ON di.ingredient_id=i.ingredient_id
                LIMIT ? OFFSET ?
                """;

        List<Ingredient> ingredientList = new ArrayList<>();
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, page * size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ingredientList.add(
                            new Ingredient(
                                    rs.getLong("ingredient_id"),
                                    rs.getString("ingredient_name"),
                                    rs.getDouble("quantity"),
                                    Unit.valueOf(rs.getString("unit")),
                                    getPriceListOfIngredient(rs.getLong("ingredient_id")),
                                    getStockMovementListOfIngredient(rs.getLong("ingredient_id"))
                            )
                    );
                }
            }
            return ingredientList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient getIngredientById(Long id) {
        String sql = """
                SELECT i.ingredient_id, i.ingredient_name, di.quantity, i.unit
                FROM DishIngredient di JOIN Ingredient i ON di.ingredient_id=i.ingredient_id
                WHERE i.ingredient_id = ?
                """;
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ingredient(
                            rs.getLong("ingredient_id"),
                            rs.getString("ingredient_name"),
                            rs.getDouble("quantity"),
                            Unit.valueOf(rs.getString("unit")),
                            getPriceListOfIngredient(rs.getLong("ingredient_id")),
                            getStockMovementListOfIngredient(rs.getLong("ingredient_id"))
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        String sql = """
                    INSERT INTO Ingredient (ingredient_id, ingredient_name, unit) VALUES(?, ?, ?)
                    ON CONFLICT (ingredient_id) DO UPDATE
                    SET ingredient_id=EXCLUDED.ingredient_id, 
                    ingredient_name=EXCLUDED.ingredient_name,
                    unit=EXCLUDED.unit
                """;
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, ingredient.getId());
            ps.setString(2, ingredient.getName());
            ps.setString(3, ingredient.getUnit().name());
            insertQuantityOfIngredient(ingredient.getQuantity());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ingredient;
    }

    private void insertQuantityOfIngredient(Double quantity) {
        String sql = """
                INSERT INTO DishIngredient(quantity) VALUES (?)
                ON CONFLICT (quantity) DO UPDATE 
                SET quantity = EXCLUDED.quantity 
                """;

        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, quantity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

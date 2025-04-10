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
                SELECT p.price_id, p.amount, p.dateTime FROM Price p JOIN 
                Ingredient i ON p.ingredient_id = ? 
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
                FROM StockMovement p JOIN 
                Ingredient i ON p.ingredient_id = ? 
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
                """;

        List<Ingredient> ingredientList = new ArrayList<>();
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
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
}

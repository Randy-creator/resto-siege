package org.restaurantmanagement.resto.repository.dao;

import org.restaurantmanagement.resto.entity.*;
import org.restaurantmanagement.resto.repository.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientCrudImpl implements IngredientCrud {
    private final DbConnection db;

    @Autowired
    public IngredientCrudImpl(DbConnection db) {
        this.db = db;
    }

    public List<Price> getPriceListOfIngredient(Long id) {
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

    public List<StockMovement> getStockMovementListOfIngredient(Long id) {
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
            insertQuantityOfIngredient(ingredient.getId(), ingredient.getQuantity());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ingredient;
    }


    private void insertQuantityOfIngredient(Long ingredientId, Double quantity) {
        String sql = """
                INSERT INTO DishIngredient(ingredient_id, quantity) VALUES (?, ?)
                ON CONFLICT (ingredient_id) DO UPDATE 
                SET quantity = EXCLUDED.quantity 
                """;

        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, ingredientId);
            ps.setDouble(2, quantity);

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient save(Long id, List<StockMovement> stockMovementList, List<Price> priceList) {
        Ingredient ingredientToSave = getIngredientById(id);
        ingredientToSave.setStockMovements(stockMovementList);
        ingredientToSave.setPrices(priceList);

        try (Connection connection = db.getConnection()) {
            for (StockMovement stockMove : stockMovementList) {
                String sql = """
                        INSERT INTO StockMovement (stock_movement_id, ingredient_id, quantity, unit, stockMovementType, creationDateTime)
                        VALUES(?, ?, ?, ?, ?, ?)
                        ON CONFLICT (stock_movement_id) DO UPDATE
                        SET ingredient_id=EXCLUDED.ingredient_id,
                            quantity=EXCLUDED.quantity, unit=EXCLUDED.unit, 
                            stockMovementType=EXCLUDED.stockMovementType, 
                            creationDateTime=EXCLUDED.creationDateTime
                        """;

                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setLong(1, stockMove.getId());
                    ps.setLong(2, ingredientToSave.getId());
                    ps.setDouble(3, stockMove.getQuantity());
                    ps.setString(4, stockMove.getUnit().name());
                    ps.setString(5, stockMove.getStockMovementType().name());
                    ps.setTimestamp(6, Timestamp.valueOf(stockMove.getCreationDateTime()));
                    ps.executeUpdate();
                }
            }

            for (Price price : priceList) {
                String sql = """
                        INSERT INTO Price (price_id, ingredient_id, amount, dateTime)
                        VALUES (?, ?, ?, ?)
                        ON CONFLICT (price_id) DO UPDATE
                        SET ingredient_id = EXCLUDED.ingredient_id,
                            amount = EXCLUDED.amount,
                            dateTime = EXCLUDED.dateTime
                        """;

                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setLong(1, price.getId());
                    ps.setLong(2, id);
                    ps.setDouble(3, price.getAmount());
                    ps.setTimestamp(4, Timestamp.valueOf(price.getDateTime()));
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ingredientToSave;
    }

    @Override
    public Ingredient savePrices(Long id, List<Price> priceList) {
        Ingredient ingredient = getIngredientById(id);
        ingredient.setPrices(priceList);

        try (Connection connection = db.getConnection()) {
            for (Price price : priceList) {
                String sql = """
                        INSERT INTO Price (price_id, ingredient_id, amount, dateTime)
                        VALUES (?, ?, ?, ?)
                        ON CONFLICT (price_id) DO UPDATE
                        SET ingredient_id = EXCLUDED.ingredient_id,
                            amount = EXCLUDED.amount,
                            dateTime = EXCLUDED.dateTime
                        """;

                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setLong(1, price.getId());
                    ps.setLong(2, id);
                    ps.setDouble(3, price.getAmount());
                    ps.setTimestamp(4, Timestamp.valueOf(price.getDateTime()));
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ingredient;
    }

    @Override
    public Ingredient saveStockMovements(Long id, List<StockMovement> stockMovementList) {
        Ingredient ingredient = getIngredientById(id);
        ingredient.setStockMovements(stockMovementList);

        try (Connection connection = db.getConnection()) {
            for (StockMovement stockMove : stockMovementList) {
                String sql = """
                        INSERT INTO StockMovement (stock_movement_id, ingredient_id, quantity, unit, stockMovementType, creationDateTime)
                        VALUES(?, ?, ?, ?, ?, ?)
                        ON CONFLICT (stock_movement_id) DO UPDATE
                        SET ingredient_id=EXCLUDED.ingredient_id,
                            quantity=EXCLUDED.quantity, unit=EXCLUDED.unit, 
                            stockMovementType=EXCLUDED.stockMovementType, 
                            creationDateTime=EXCLUDED.creationDateTime
                        """;

                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setLong(1, stockMove.getId());
                    ps.setLong(2, ingredient.getId());
                    ps.setDouble(3, stockMove.getQuantity());
                    ps.setString(4, stockMove.getUnit().name());
                    ps.setString(5, stockMove.getStockMovementType().name());
                    ps.setTimestamp(6, Timestamp.valueOf(stockMove.getCreationDateTime()));
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ingredient;
    }

}

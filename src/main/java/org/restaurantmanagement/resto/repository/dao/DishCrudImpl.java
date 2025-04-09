package org.restaurantmanagement.resto.repository.dao;

import org.restaurantmanagement.resto.entity.Dish;
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

    public DishCrudImpl(DbConnection db) {
        this.db = db;
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
                            rs.getDouble("price")
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

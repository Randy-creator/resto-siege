package org.restaurantmanagement.resto.repository;

import org.restaurantmanagement.resto.configuration.DbConnection;
import org.restaurantmanagement.resto.entity.model.Sale;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class SaleCrudImpl implements SaleCrud {

    private DbConnection db = new DbConnection();

    public SaleCrudImpl(DbConnection db) {
        this.db = db;
    }

    @Override
    public void saveAll(List<Sale> sales) {
        String sql = "INSERT INTO sale (dish_name, sale_quantity, total_earned) VALUES (?, ?, ?)";

        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            for (Sale sale : sales) {
                ps.setString(1, sale.getDishName());
                ps.setDouble(2, sale.getSaleQuantity());
                ps.setDouble(3, sale.getTotalEarned());
                ps.addBatch();
            }

            ps.executeBatch(); // Exécute tout d’un coup 🚀

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

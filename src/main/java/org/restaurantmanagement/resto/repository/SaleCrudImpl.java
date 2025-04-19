package org.restaurantmanagement.resto.repository;

import org.restaurantmanagement.resto.configuration.DbConnection;
import org.restaurantmanagement.resto.entity.model.Sale;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SaleCrudImpl implements SaleCrud {

    private DbConnection db = new DbConnection();

    public SaleCrudImpl(DbConnection db) {
        this.db = db;
    }

    @Override
    public void saveAll(List<Sale> sales, String branchName) {
        String sql = """
                INSERT INTO sale (branch_name, dish_name, sale_quantity, total_earned) 
                VALUES (?, ?, ?, ?) 
                ON CONFLICT (branch_name, dish_name) DO UPDATE SET 
                    sale_quantity = excluded.sale_quantity, 
                    total_earned = excluded.total_earned
                """;

        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            for (Sale sale : sales) {
                ps.setString(1, branchName);
                ps.setString(2, sale.getDishName());
                ps.setDouble(3, sale.getSaleQuantity());
                ps.setDouble(4, sale.getTotalEarned());
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Sale> getBestSale(int top) {
        String sql = """
                SELECT dish_name, sale_quantity, total_earned FROM Sale ORDER BY total_earned DESC 
                LIMIT ?                
                """;

        List<Sale> saleList = new ArrayList<>();
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, top);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    saleList.add(new Sale(
                            rs.getString("dish_name"),
                            rs.getDouble("sale_quantity"),
                            rs.getDouble("total_earned")
                    ));
                }
            }
            return saleList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

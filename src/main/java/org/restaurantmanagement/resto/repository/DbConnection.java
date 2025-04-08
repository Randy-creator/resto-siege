package org.restaurantmanagement.resto.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    Connection connection = null;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

//   "jdbc:postgresql://localhost:5432/restaurant"

}

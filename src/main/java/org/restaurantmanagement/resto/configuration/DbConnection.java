package org.restaurantmanagement.resto.configuration;

import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DbConnection {
    Connection connection = null;

    public Connection getConnection() {
        try {
            connection =
                    DriverManager.getConnection(System.getenv("url"),
                            System.getenv("user"),
                            System.getenv("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


}

package org.restaurantmanagement.resto.repository.dao;

import org.restaurantmanagement.resto.entity.DishOrder;
import org.restaurantmanagement.resto.entity.Order;
import org.restaurantmanagement.resto.entity.StatusType;
import org.restaurantmanagement.resto.repository.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderCrudImpl implements OrderCrud {
    private final DbConnection datasource;
    private final DishOrderCrupImpl dishOrderDao;
    private final StatusCrudImpl statusDao;

    @Autowired
    public OrderCrudImpl(
            DbConnection datasource,
            DishOrderCrupImpl dishOrderDao,
            StatusCrudImpl statusDao
    ) {
        this.datasource = datasource;
        this.dishOrderDao = dishOrderDao;
        this.statusDao = statusDao;
    }

    @Override
    public List<Order> getAll(int pageSize, int pageNumber) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id, order_reference from orders LIMIT ? OFFSET ?;";
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            int offset = pageSize * (pageNumber - 1);
            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, offset);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order(
                            resultSet.getLong("order_id"),
                            resultSet.getString("order_reference")

                    );
                    order.setOrderedDish(dishOrderDao.getDishOrdersByOrderId(resultSet.getLong("order_id")));
                    order.setStatus(statusDao.getStatusForOrder(resultSet.getLong("order_id")));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public Optional<Order> getByReference(String reference) {
        String query = "SELECT order_id, order_reference from orders where order_reference ILIKE ?";
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, "%" + reference + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Order order = new Order(
                        resultSet.getLong("order_id"),
                        resultSet.getString("order_reference")
                );
                order.setOrderedDish(dishOrderDao.getDishOrdersByOrderId(resultSet.getLong("order_id")));
                order.setStatus(statusDao.getStatusForOrder(resultSet.getLong("order_id")));
                return Optional.of(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Order getByID(long orderId) {
        String query = "SELECT order_id, order_reference from orders where order_id = ?";
        Order order = null;
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                order = new Order(
                        resultSet.getLong("order_id"),
                        resultSet.getString("order_reference")
                );
                order.setOrderedDish(dishOrderDao.getDishOrdersByOrderId(resultSet.getLong("order_id")));
                order.setStatus(statusDao.getStatusForOrder(resultSet.getLong("order_id")));
            } else {
                System.out.println("No order found with ID: " + orderId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (order != null) {
            order.setStatus(statusDao.getStatusForOrder(orderId));
        }
        return order;
    }

    @Override
    public void createOrder(Order order) throws SQLException {
        String sqlForOrders = "INSERT INTO orders (order_id, order_reference) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection connection = datasource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlForOrders)) {
                statement.setLong(1, order.getOrderID());
                statement.setString(2, order.getReference());
                statement.executeUpdate();
            }
        }
        statusDao.insertStatusForOrder(order.getOrderID(), StatusType.CREATED);
    }

    @Override
    public void save(Order order) throws SQLException {
        if (!order.validateCommand()) {
            throw new RuntimeException("Not enough Ingredients");
        }

        if (!order.getOrderedDish().isEmpty()) {
            statusDao.insertStatusForOrder(order.getOrderID(), StatusType.CONFIRMED);
            for (DishOrder dishOrder : order.getOrderedDish()) {
                dishOrderDao.addDishOrder(dishOrder, order.getOrderID());
            }
        }
    }

    @Override
    public void updateStatus(StatusType statusType, long orderId) {
        Order order = getByID(orderId);
        if (statusType == StatusType.CONFIRMED) {
            throw new RuntimeException("Cannot perform that action");
        }
        if (statusType == StatusType.FINISHED && !order.allTheDishesFinished()) {
            throw new RuntimeException("All the dishes are not finished!");
        }
        if (statusType == StatusType.SERVED && order.getActualStatus() != StatusType.FINISHED && !order.getOrderedDish().isEmpty()) {
            throw new RuntimeException("All the dishes are not finished yet!");
        } else {
            statusDao.insertStatusForOrder(orderId, statusType);
        }
    }
}

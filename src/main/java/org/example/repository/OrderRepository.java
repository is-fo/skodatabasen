package org.example.repository;

import org.example.data.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderRepository extends Repository<Order> {

    protected OrderRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(Order entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Optional<Order> find(Integer id) {
        return find(Order.table(), id, resultSet -> new Order(
                resultSet.getInt("id"),
                resultSet.getInt("kundId"),
                resultSet.getDate("created"),
                resultSet.getBoolean("active")));
    }

    List<Order> findAll() {
        return findAll(Order.table(), resultSet -> new Order(
                resultSet.getInt("id"),
                resultSet.getInt("kundId"),
                resultSet.getDate("created"),
                resultSet.getBoolean("active")
        ));
    }

    Integer findLatestOrderForCustomer(Integer kundId) {
        Integer latestOrderId = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT b.id FROM beställning b WHERE b.kundId = ? AND active = TRUE""")) {
            statement.setInt(1, kundId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    latestOrderId = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            errorLogger.logError(e);
        }
        return latestOrderId;
    }
}

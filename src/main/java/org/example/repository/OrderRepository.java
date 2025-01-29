package org.example.repository;

import org.example.data.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class OrderRepository extends Repository<Order> {

    protected OrderRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(Order entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    Optional<Order> find(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM beställning WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Order(
                            resultSet.getInt("id"),
                            resultSet.getInt("kundId"),
                            resultSet.getDate("created"),
                            true
                    ));
                }
            }
        } catch (SQLException ex) {
            errorLogger.logError(ex);
        }
        return Optional.empty();
    }

    @Override
    Iterable<Order> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void update(Order entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void delete(Order entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll(Iterable<? extends Order> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

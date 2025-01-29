package org.example.repository;

import org.example.data.OrderDetailed;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class OrderDetailedRepository extends Repository<OrderDetailed> {

    protected OrderDetailedRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(OrderDetailed entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    Optional<OrderDetailed> find(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM beställning_details WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new OrderDetailed(
                            resultSet.getInt("id"),
                            resultSet.getInt("orderId"),
                            resultSet.getInt("sko_detailsId"),
                            resultSet.getInt("antal")
                    ));
                }
            }
        } catch (SQLException ex) {
            errorLogger.logError(ex);
        }
        return Optional.empty();
    }

    @Override
    Iterable<OrderDetailed> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void update(OrderDetailed entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void delete(OrderDetailed entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll(Iterable<? extends OrderDetailed> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

package org.example.repository;

import org.example.data.Shoe;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ShoeRepository extends Repository<Shoe> {

    protected ShoeRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(Shoe entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    Optional<Shoe> find(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM sko WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Shoe(
                            resultSet.getInt("id"),
                            resultSet.getInt("pris"),
                            resultSet.getString("brand"),
                            resultSet.getString("color")
                    ));
                }
            }
        } catch (SQLException ex) {
            errorLogger.logError(ex);
        }
        return Optional.empty();
    }

    @Override
    Iterable<Shoe> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void update(Shoe entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void delete(Shoe entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll(Iterable<? extends Shoe> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

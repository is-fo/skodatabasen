package org.example.repository;

import org.example.data.ShoeDetailed;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ShoeDetailedRepository extends Repository<ShoeDetailed> {

    protected ShoeDetailedRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(ShoeDetailed entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    Optional<ShoeDetailed> find(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM sko_details WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new ShoeDetailed(
                            resultSet.getInt("id"),
                            resultSet.getInt("antal"),
                            resultSet.getInt("storlek"),
                            resultSet.getInt("skoId")
                    ));
                }
            }
        } catch (SQLException ex) {
            errorLogger.logError(ex);
        }
        return Optional.empty();
    }

    @Override
    Iterable<ShoeDetailed> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void update(ShoeDetailed entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void delete(ShoeDetailed entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll(Iterable<? extends ShoeDetailed> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

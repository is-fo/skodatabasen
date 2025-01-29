package org.example.repository;

import org.example.data.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CategoryRepository extends Repository<Category> {

    protected CategoryRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(Category entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    Optional<Category> find(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM kategori WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Category(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    ));
                }
            }
        } catch (SQLException ex) {
            errorLogger.logError(ex);
        }
        return Optional.empty();
    }

    @Override
    Iterable<Category> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void update(Category entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void delete(Category entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll(Iterable<? extends Category> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

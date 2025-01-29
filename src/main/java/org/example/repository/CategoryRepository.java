package org.example.repository;

import org.example.data.Category;

import javax.sql.DataSource;
import java.util.Optional;

public class CategoryRepository extends Repository<Category> {

    protected CategoryRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(Category entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Optional<Category> find(Integer id) {
        return find("kategori", id, resultSet -> new Category(
                resultSet.getInt("id"),
                resultSet.getString("namn")));
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

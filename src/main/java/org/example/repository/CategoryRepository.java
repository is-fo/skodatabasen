package org.example.repository;

import org.example.data.Category;

import javax.sql.DataSource;
import java.util.List;
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
        return find(Category.table(), id, resultSet -> new Category(
                resultSet.getInt("id"),
                resultSet.getString("namn")));
    }

    List<Category> findAll() {
        return findAll(Category.table(), resultSet -> new Category(
                resultSet.getInt("id"),
                resultSet.getString("namn")
        ));
    }
}

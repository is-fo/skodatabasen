package org.example.repository;

import org.example.data.Shoe;

import javax.sql.DataSource;
import java.util.Optional;

public class ShoeRepository extends Repository<Shoe> {

    protected ShoeRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(Shoe entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Optional<Shoe> find(Integer id) {
        return find("sko", id, resultSet -> new Shoe(
                resultSet.getInt("id"),
                resultSet.getInt("pris"),
                resultSet.getString("brand"),
                resultSet.getString("color")));
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

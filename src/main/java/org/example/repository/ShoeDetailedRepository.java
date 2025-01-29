package org.example.repository;

import org.example.data.ShoeDetailed;

import javax.sql.DataSource;
import java.util.Optional;

public class ShoeDetailedRepository extends Repository<ShoeDetailed> {

    protected ShoeDetailedRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(ShoeDetailed entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Optional<ShoeDetailed> find(Integer id) {
        return find(ShoeDetailed.table(), id, resultSet -> new ShoeDetailed(
                resultSet.getInt("id"),
                resultSet.getInt("antal"),
                resultSet.getInt("storlek"),
                resultSet.getInt("skoId")));
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

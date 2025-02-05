package org.example.repository;

import org.example.data.ShoeDetailed;

import javax.sql.DataSource;
import java.util.List;
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

    List<ShoeDetailed> findAll() {
        return findAll(ShoeDetailed.table(), resultSet -> new ShoeDetailed(
                resultSet.getInt("id"),
                resultSet.getInt("antal"),
                resultSet.getInt("storlek"),
                resultSet.getInt("skoId")
        ));
    }
}

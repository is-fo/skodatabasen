package org.example.repository;

import org.example.data.Shoe;

import javax.sql.DataSource;
import java.util.List;
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
        return find(Shoe.table(), id, resultSet -> new Shoe(
                resultSet.getInt("id"),
                resultSet.getInt("pris"),
                resultSet.getString("brand"),
                resultSet.getString("color")));
    }

    List<Optional<Shoe>> findAll() {
        return findAll(Shoe.table(), resultSet -> new Shoe(
                resultSet.getInt("id"),
                resultSet.getInt("pris"),
                resultSet.getString("brand"),
                resultSet.getString("color")
        ));
    }
}

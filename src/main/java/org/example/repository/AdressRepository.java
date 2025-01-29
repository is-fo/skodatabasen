package org.example.repository;

import org.example.data.Adress;

import javax.sql.DataSource;
import java.util.Optional;

public class AdressRepository extends Repository<Adress> {

    protected AdressRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void insert(Adress entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Optional<Adress> find(Integer id) {
        return find("adress", id, resultSet -> new Adress(
                resultSet.getInt("id"),
                resultSet.getString("postnr"),
                resultSet.getString("ort"),
                resultSet.getString("gatunamn"),
                resultSet.getString("husnummer")
        ));
    }

    @Override
    public Iterable<Adress> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Adress entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Adress entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteAll(Iterable<? extends Adress> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

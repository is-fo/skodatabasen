package org.example.repository;

import org.example.data.Adress;

import javax.sql.DataSource;
import java.util.List;
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
        return find(Adress.table(), id, resultSet -> new Adress(
                resultSet.getInt("id"),
                resultSet.getString("postnr"),
                resultSet.getString("ort"),
                resultSet.getString("gatunamn"),
                resultSet.getString("husnummer")
        ));
    }

    public List<Optional<Adress>> findAll() {
        return findAll(Adress.table(), resultSet -> new Adress(
                resultSet.getInt("id"),
                resultSet.getString("postnr"),
                resultSet.getString("ort"),
                resultSet.getString("gatunamn"),
                resultSet.getString("husnummer")
        ));
    }
}

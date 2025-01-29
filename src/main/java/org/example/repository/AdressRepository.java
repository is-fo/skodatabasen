package org.example.repository;

import org.example.data.Adress;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AdressRepository extends Repository<Adress> {

    protected AdressRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void insert(Adress entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Adress> find(Integer id) {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM adress WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Adress(
                            resultSet.getInt("id"),
                            resultSet.getString("postnr"),
                            resultSet.getString("ort"),
                            resultSet.getString("gatunamn"),
                            resultSet.getString("husnummer")
                    ));
                }
            }
        } catch (SQLException ex) {
            errorLogger.logError(ex);
        }
        return Optional.empty();
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

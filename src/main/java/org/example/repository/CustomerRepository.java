package org.example.repository;

import org.example.data.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerRepository extends Repository<Customer> {

    protected CustomerRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void insert(Customer entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Customer> find(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM kund WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Customer(
                            resultSet.getInt("id"),
                            resultSet.getString("namn"),
                            resultSet.getString("email"),
                            resultSet.getString("telefon"),
                            resultSet.getInt("adressId")
                    ));
                }
            }
        } catch (SQLException ex) {
            errorLogger.logError(ex);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Customer> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Customer entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Customer entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

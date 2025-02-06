package org.example.repository;

import org.example.data.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerRepository extends Repository<Customer> {

    protected CustomerRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void insert(Customer entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Optional<Customer> find(Integer id) {
        return find(Customer.table(), id, resultSet -> new Customer(
                resultSet.getInt("id"),
                resultSet.getString("namn"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getString("telefon"),
                resultSet.getInt("adressId")));
    }

    public List<Customer> findAll() {
        return findAll(Customer.table(), resultSet -> new Customer(
                resultSet.getInt("id"),
                resultSet.getString("namn"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getString("telefon"),
                resultSet.getInt("adressId")
        ));
    }

    public Optional<Customer> findByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kund WHERE email = ?")) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Customer(
                            resultSet.getInt("id"),
                            resultSet.getString("namn"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("telefon"),
                            resultSet.getInt("adressId")
                    ));
                }
            }
        } catch (SQLException e) {
            errorLogger.logError(e);
        }
        return Optional.empty();
    }
}

package org.example.repository;

import org.example.data.Customer;

import javax.sql.DataSource;
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

    public List<Optional<Customer>> findAll() {
        return findAll(Customer.table(), resultSet -> new Customer(
                resultSet.getInt("id"),
                resultSet.getString("namn"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getString("telefon"),
                resultSet.getInt("adressId")
        ));
    }
}

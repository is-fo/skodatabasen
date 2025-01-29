package org.example.repository;

import org.example.data.Customer;

import javax.sql.DataSource;
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
        return find("kund", id, resultSet -> new Customer(
                resultSet.getInt("id"),
                resultSet.getString("namn"),
                resultSet.getString("email"),
                resultSet.getString("telefon"),
                resultSet.getInt("adressId")));
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

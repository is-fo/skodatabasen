package org.example.repository;

import org.example.data.Order;

import javax.sql.DataSource;
import java.util.Optional;

public class OrderRepository extends Repository<Order> {

    protected OrderRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(Order entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Optional<Order> find(Integer id) {
        return find(Order.table(), id, resultSet -> new Order(
                resultSet.getInt("id"),
                resultSet.getInt("kundId"),
                resultSet.getDate("created"),
                resultSet.getBoolean("active")));
    }

    @Override
    Iterable<Order> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void update(Order entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void delete(Order entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll(Iterable<? extends Order> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}

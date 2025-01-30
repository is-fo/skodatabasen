package org.example.repository;

import org.example.data.Order;

import javax.sql.DataSource;
import java.util.List;
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

    List<Optional<Order>> findAll() {
        return findAll(Order.table(), resultSet -> new Order(
                resultSet.getInt("id"),
                resultSet.getInt("kundId"),
                resultSet.getDate("created"),
                resultSet.getBoolean("active")
        ));
    }
}

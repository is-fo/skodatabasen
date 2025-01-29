package org.example.repository;

import org.example.data.OrderDetailed;

import javax.sql.DataSource;
import java.util.Optional;

public class OrderDetailedRepository extends Repository<OrderDetailed> {

    protected OrderDetailedRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(OrderDetailed entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Optional<OrderDetailed> find(Integer id) {
        return find(OrderDetailed.table(), id, resultSet -> new OrderDetailed(
                resultSet.getInt("id"),
                resultSet.getInt("orderId"),
                resultSet.getInt("sko_detailsId"),
                resultSet.getInt("antal")));
    }

    @Override
    Iterable<OrderDetailed> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void update(OrderDetailed entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void delete(OrderDetailed entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll(Iterable<? extends OrderDetailed> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

package org.example.repository;

import org.example.data.OutOfStock;

import javax.sql.DataSource;
import java.util.Optional;

public class OutOfStockRepository extends Repository<OutOfStock> {

    public OutOfStockRepository(DataSource dataSource) {
        super(dataSource);
    }

    Optional<OutOfStock> find(Integer id) {
        return find(OutOfStock.table(), id, resultSet -> new OutOfStock(
                resultSet.getInt("id"),
                resultSet.getInt("sko_detailsId")
        ));
    }

    @Override
    void insert(OutOfStock entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    Iterable<OutOfStock> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void update(OutOfStock entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void delete(OutOfStock entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void deleteAll(Iterable<? extends OutOfStock> outOfStocks) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

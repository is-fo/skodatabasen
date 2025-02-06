package org.example.repository;

import org.example.data.Shoe;
import org.example.data.ShoeDetailed;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoeDetailedRepository extends Repository<ShoeDetailed> {

    protected ShoeDetailedRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(ShoeDetailed entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Optional<ShoeDetailed> find(Integer id) {
        return find(ShoeDetailed.table(), id, resultSet -> new ShoeDetailed(
                resultSet.getInt("id"),
                resultSet.getInt("antal"),
                resultSet.getInt("storlek"),
                resultSet.getInt("skoId")));
    }

    List<ShoeDetailed> findAll() {
        return findAll(ShoeDetailed.table(), resultSet -> new ShoeDetailed(
                resultSet.getInt("id"),
                resultSet.getInt("antal"),
                resultSet.getInt("storlek"),
                resultSet.getInt("skoId")
        ));
    }

    List<ShoeDetailed> findByShoe(Shoe shoe) {
        List<ShoeDetailed> shoeDetaileds = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM sko_details sd " +
                     "JOIN sko s ON s.id = sd.skoId " +
                     "WHERE s.id = ?")) {
            statement.setInt(1, shoe.id());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    shoeDetaileds.add(new ShoeDetailed(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4)
                    ));
                }
            }
        } catch (SQLException e) {
            errorLogger.logError(e);
        }
        return shoeDetaileds;
    }
}

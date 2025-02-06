package org.example.repository;

import org.example.data.Category;
import org.example.data.Shoe;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoeRepository extends Repository<Shoe> {

    protected ShoeRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void insert(Shoe entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Optional<Shoe> find(Integer id) {
        return find(Shoe.table(), id, resultSet -> new Shoe(
                resultSet.getInt("id"),
                resultSet.getInt("pris"),
                resultSet.getString("brand"),
                resultSet.getString("color")));
    }

    List<Shoe> findAll() {
        return findAll(Shoe.table(), resultSet -> new Shoe(
                resultSet.getInt("id"),
                resultSet.getInt("pris"),
                resultSet.getString("brand"),
                resultSet.getString("color")
        ));
    }

    public List<Shoe> findShoesInCategory(Category category) {
        List<Shoe> shoes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareCall("""
                     SELECT * FROM sko s
                     JOIN kat_tillhörighet kt ON s.id = kt.skoId
                     JOIN kategori k ON kt.katId = k.id
                     WHERE k.id = ?;
                     """)) {
            statement.setInt(1, category.id());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shoes.add(new Shoe(
                        rs.getInt("id"),
                        rs.getInt("pris"),
                        rs.getString("brand"),
                        rs.getString("color")
                ));
            }
            return shoes;
        } catch (SQLException e) {
            errorLogger.logError(e);
        }
        return shoes;
    }
}

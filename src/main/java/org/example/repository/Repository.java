package org.example.repository;

import org.example.data.Entity;
import org.example.data.RowMapper;
import org.example.logs.ConsoleErrorLogger;
import org.example.logs.ErrorLogger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * <a href="https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html">Interfacet definerar grundläggande CRUD operationer</a>
 * <br>
 * <a href="https://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-j2ee-concepts-connection-pooling.html">och connection pooling</a>
 * <br>
 * @param <ENTITY> the type of the entity which extends {@link Entity}
 */
public abstract class Repository<ENTITY extends Entity> {

    protected final DataSource dataSource;
    protected final ErrorLogger errorLogger = new ConsoleErrorLogger();

    protected Repository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    abstract void insert(ENTITY entry);

    Optional<ENTITY> find(String table, Integer id, RowMapper<ENTITY> mapper) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapper.mapRow(resultSet));
                }
            }
        } catch (SQLException ex) {
            errorLogger.logError(ex);
        }
        return Optional.empty();
    }
    abstract Iterable<ENTITY> findAll();
    abstract void update(ENTITY entry);
    abstract void delete(ENTITY entry);
    abstract void deleteAll();
    abstract void deleteAll(Iterable<? extends ENTITY> entities);
}

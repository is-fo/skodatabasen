package org.example.repository;

import org.example.data.Entity;
import org.example.logs.ConsoleErrorLogger;
import org.example.logs.ErrorLogger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * <a href="https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html">Interfacet definerar grundläggande CRUD operationer</a>
 * <br>
 * <a href="https://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-j2ee-concepts-connection-pooling.html">och connection pooling</a>
 * <br>
 *
 * @param <ENTITY> the type of the entity which extends {@link Entity}
 */
public abstract class Repository<ENTITY extends Entity> {

    protected final DataSource dataSource;
    protected final ErrorLogger errorLogger = new ConsoleErrorLogger();

    protected Repository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    abstract void insert(ENTITY entry);

    protected Optional<ENTITY> find(String table, Integer id, RowMapper<ENTITY> mapper) {
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

    protected List<ENTITY> findAll(String table, RowMapper<ENTITY> mapper) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<ENTITY> entities = new ArrayList<>();
                while (resultSet.next()) {
                    ENTITY entry = mapper.mapRow(resultSet);
                    entities.add(entry);
                }
                return entities;
            }
        } catch (SQLException ex) {
            errorLogger.logError(ex);
        }

         return new ArrayList<>(); //hm
    }


    //TODO
//    abstract void update(ENTITY entry);
//
//    abstract void delete(ENTITY entry);
//
//    abstract void deleteAll();
//
//    abstract void deleteAll(List<Optional<? extends ENTITY>> entities);
}

/**
 * <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/RowMapper.html">inspiration</a>
 * <br> RowMapper is a <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html"> functional interface</a>  used
 * to map instances of {@link ResultSet} to {@link Entity} objects.
 *
 * @param <ENTITY> the data entity which extends {@link Entity}
 */

@FunctionalInterface
interface RowMapper<ENTITY extends Entity> {
    /**
     * @param rs the {@link ResultSet} to be mapped
     * @return an instance of a concrete implementation of {@link Entity}
     * @throws SQLException
     */
    ENTITY mapRow(ResultSet rs) throws SQLException;
}

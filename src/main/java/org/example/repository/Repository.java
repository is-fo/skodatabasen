package org.example.repository;

import org.example.data.DataEntry;
import org.example.logs.ConsoleErrorLogger;
import org.example.logs.ErrorLogger;

import javax.sql.DataSource;
import java.util.Optional;

/**
 * <a href="https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html">Interfacet definerar grundläggande CRUD operationer</a>
 * <br>
 * <a href="https://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-j2ee-concepts-connection-pooling.html">och connection pooling</a>
 * @param <T> the type of the entity which extends {@link DataEntry}
 */
public abstract class Repository<T extends DataEntry> {

    protected DataSource dataSource;
    protected ErrorLogger errorLogger = new ConsoleErrorLogger();

    protected Repository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    abstract void insert(T entity);
    abstract Optional<T> find(Integer id);
    abstract Iterable<T> findAll();
    abstract void update(T entry);
    abstract void delete(T entry);
    abstract void deleteAll();
    abstract void deleteAll(Iterable<? extends T> entities);
}

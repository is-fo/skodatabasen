package org.example.repository;

import org.example.data.DataEntry;

import java.util.Optional;

/**
 * <a href="https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html">Interfacet definerar grundläggande CRUD operationer</a>
 * <br>
 * <a href="https://docs.spring.io/spring-data/jpa/reference/repositories/core-concepts.html">Inspiration tagen härifrån</a>
 * @param <T> the type of the entity which extends {@link DataEntry}
 */
public interface Repository<T extends DataEntry> {
    void insert(T entity);
    Optional<T> find(Integer id);
    Iterable<T> findAll();
    void update(T entry);
    void delete(T entry);
    void deleteAll();
    void deleteAll(Iterable<? extends T> entities);
}

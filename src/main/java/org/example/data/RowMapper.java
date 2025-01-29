package org.example.data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/RowMapper.html">inspiration</a>
 * <br> RowMapper is a <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html"> functional interface</a>  used
 * to map instances of {@link ResultSet} to {@link Entity} objects.
 * @param <ENTITY> the data entity which extends {@link Entity}
 */

@FunctionalInterface
public interface RowMapper<ENTITY extends Entity> {
    /**
     *
     * @param rs the {@link ResultSet} to be mapped
     * @return an instance of a concrete implementation of {@link Entity}
     * @throws SQLException
     */
    ENTITY mapRow(ResultSet rs) throws SQLException;
}

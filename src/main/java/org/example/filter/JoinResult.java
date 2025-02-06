package org.example.filter;

import org.example.data.Entity;

import java.util.List;

public record JoinResult<ID extends Entity, E extends Entity>(ID id, List<E> entities) {

}

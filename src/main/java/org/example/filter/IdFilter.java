package org.example.filter;

import org.example.data.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 *
 * {@code SELECT *
 * FROM entities e
 * JOIN (tabellen ID kommer ifrån) in ON e.foreign_key = in.ID
 * WHERE in.ID = p_key;}
 * <p>Meningen är att den här klassen ska fungera som en inner join men just nu sparar den inte datat från ID tabellen.
 * Bara tänkt att användas för tabeller som har 1:M sammanband
 * det går att göra typ nestade <a href="https://dzone.com/articles/using-filter-design-pattern-in-java">Filter</a> men det känns konstigt.
 * <br>
 * För att komma åt referensvärdena används <a href="https://openjdk.org/jeps/406">pattern matching med switch</a>
 * för att undvika att join-beteenden ska vara hårdkodade används vanlig nestlad pattern matching för att undvika felskrivningar som ändå "fungerar".
 * @param <ID> Primärnyckeln som refereras av {@link E}.
 * @param <E> Representerar tabellen där som innehåller referensnycklar till {@link ID}
 */

public class IdFilter<ID extends Entity, E extends Entity> {

    public List<E> join(Optional<ID> p_key, List<E> entities) {
        if (p_key.isEmpty() || entities.isEmpty()) {
            throw new IllegalArgumentException("Parameters cannot be empty");
        }

        return entities.stream().filter(e -> switch (e) {
            case Adress a -> throw new IllegalArgumentException(a.getClass().getSimpleName() + " has no foreign key");
            case Category c -> throw new IllegalArgumentException(c.getClass().getSimpleName() + " has no foreign key");
            case Customer c -> {
                if (p_key.get() instanceof Adress a) {
                    yield c.fk_adress().equals(a.id());
                } else {
                    throw new IllegalArgumentException("Invalid entity type, " + e.getClass().getSimpleName() + " has no foreign key of type : " + p_key.get().getClass().getSimpleName());
                }
            }
            case Order o -> {
                if (p_key.get() instanceof Customer c) {
                    yield o.fk_customer().equals(c.id());
                } else {
                    throw new IllegalArgumentException("Invalid entity type, " + e.getClass().getSimpleName() + " has no foreign key of type : " + p_key.get().getClass().getSimpleName());
                }
            }
            case OrderDetailed od -> {
                if (p_key.get() instanceof ShoeDetailed sd) {
                    yield od.fk_sko_detailed().equals(sd.id());
                } else if (p_key.get() instanceof Order o) {
                    yield od.fk_order().equals(o.id());
                } else {
                    throw new IllegalArgumentException("Invalid entity type, OrderDetailed has no foreign key of type : " + p_key.get().getClass());
                }
            }
            case OutOfStock oos -> {
                if (p_key.get() instanceof ShoeDetailed sd) {
                    yield oos.fk_sko_details_id().equals(sd.id());
                } else {
                    throw new IllegalArgumentException("Invalid entity type, " + e.getClass().getSimpleName() + " has no foreign key of type : " + p_key.get().getClass().getSimpleName());
                }
            }
            case Shoe s -> throw new IllegalArgumentException(s.getClass().getSimpleName() + " has no foreign key");
            case ShoeDetailed sd -> {
                if (p_key.get() instanceof Shoe s) {
                    yield sd.fk_shoe().equals(s.id());
                } else {
                    throw new IllegalArgumentException("Invalid entity type, " + e.getClass().getSimpleName() + " has no foreign key of type : " + p_key.get().getClass().getSimpleName());
                }
            }
        }).collect(Collectors.toList());
    }
}





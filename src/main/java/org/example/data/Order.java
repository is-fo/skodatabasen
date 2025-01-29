package org.example.data;

import java.time.LocalDateTime;
import java.util.Objects;

public record Order(Integer id,
                    Integer fk_customer,
                    LocalDateTime created,
                    Boolean active) implements DataEntry {
    public Order {
        Objects.requireNonNull(fk_customer);
        created = LocalDateTime.now();
    }
}

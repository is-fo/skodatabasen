package org.example.data;

import java.sql.Date;
import java.util.Objects;

public record Order(Integer id,
                    Integer fk_customer,
                    Date created,
                    Boolean active) implements Entity {
    public static String table() {
        return "beställning";
    }
    public Order {
        Objects.requireNonNull(fk_customer);
    }
}

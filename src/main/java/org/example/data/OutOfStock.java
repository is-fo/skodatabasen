package org.example.data;

import java.util.Objects;

public record OutOfStock(Integer id,
                         Integer fk_sko_details_id) implements Entity {
    public static String table() {
        return "out_of_stock";
    }
    public OutOfStock {
        Objects.requireNonNull(fk_sko_details_id);
    }
}

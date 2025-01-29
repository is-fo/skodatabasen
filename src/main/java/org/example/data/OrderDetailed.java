package org.example.data;

import java.util.Objects;

public record OrderDetailed(Integer id,
                            Integer fk_order,
                            Integer fk_sko_detailed,
                            Integer antal) implements Entity {
    public static String table() {
        return "beställning_details";
    }
    public OrderDetailed {
        Objects.requireNonNull(fk_order);
        Objects.requireNonNull(fk_sko_detailed);
    }
}

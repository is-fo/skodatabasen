package org.example.data;

import java.util.Objects;

public record Customer(Integer id,
                       String name,
                       String password,
                       String email,
                       String telephone,
                       Integer fk_adress) implements Entity {
    public static String table() {
        return "kund";
    }

    public Customer {
        Objects.requireNonNull(fk_adress);
    }
}

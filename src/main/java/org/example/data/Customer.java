package org.example.data;

import java.util.Objects;

public record Customer(Integer id,
                       String name,
                       String email,
                       String telephone,
                       Integer fk_adress) implements Entity {
    public Customer{
        Objects.requireNonNull(fk_adress);
    }
}

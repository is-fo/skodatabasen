package org.example.data;

import java.util.Objects;

public record ShoeDetailed(Integer id,
                           Integer inStock,
                           Integer size,
                           Integer fk_shoe) implements DataEntry {

    public ShoeDetailed{
        Objects.requireNonNull(fk_shoe);
    }
}

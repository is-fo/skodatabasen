package org.example.data;

public record Shoe(Integer id,
                   Integer pris,
                   String brand,
                   String color) implements DataEntry {

}

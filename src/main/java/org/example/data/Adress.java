package org.example.data;

public record Adress(Integer id,
                     String zipCode,
                     String county,
                     String streetName,
                     String houseNo) implements DataEntry {
}

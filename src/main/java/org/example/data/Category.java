package org.example.data;

public record Category(Integer id,
                       String name) implements Entity {
    public static String table() {
        return "kategori";
    }
}

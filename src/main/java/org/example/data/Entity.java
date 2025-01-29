package org.example.data;

public interface Entity {
    Integer id();

    static String table() {
        return "Entity";
    }
}

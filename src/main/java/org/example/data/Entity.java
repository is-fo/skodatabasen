package org.example.data;

public sealed interface Entity permits Adress, Category, Customer, Order, OrderDetailed, OutOfStock, Shoe, ShoeDetailed {
    Integer id();

    static String table() {
        return "Entity";
    }
}

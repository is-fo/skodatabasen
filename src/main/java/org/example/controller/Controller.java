package org.example.controller;

import org.example.logs.ConsoleErrorLogger;
import org.example.logs.ErrorLogger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Controller {

    private final DataSource ds;
    ErrorLogger errorLogger = new ConsoleErrorLogger();

    public Controller(DataSource ds) {
        this.ds = ds;
    }

    public void addToCart(int shoeDetailedID, int amount, int customerID) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareCall("CALL AddToCart(?,?,?,NULL)")) {
            statement.setInt(1, shoeDetailedID);
            statement.setInt(2, amount);
            statement.setInt(3, customerID);
            statement.execute();

        } catch (SQLException e) {
            errorLogger.logError(e);
        }
    }

    //IN IN_sko_details_id int, IN IN_antal int, IN IN_kund_id int, IN IN_best_id int)
    public void addToCart(int shoeDetailedID, int amount, int customerID, int orderID) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareCall("CALL AddToCart(?,?,?,?)")) {
            statement.setInt(1, shoeDetailedID);
            statement.setInt(2, amount);
            statement.setInt(3, customerID);
            statement.setInt(4, orderID);
            statement.execute();

        } catch (SQLException e) {
            errorLogger.logError(e);
        }
    }


    public static void main(String[] args) {

    }
}

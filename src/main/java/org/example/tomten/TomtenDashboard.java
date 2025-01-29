package org.example.tomten;

import org.example.tomten.records.Barn;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class TomtenDashboard {
    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("src\\main\\java\\secret\\secret.properties")) {
            Properties prop = new Properties();
            prop.load(fileInputStream);
            String url, user, password;
            url = prop.getProperty("db.urltomten");
            user = prop.getProperty("db.user");
                password = System.getenv("pumper");

            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    System.out.print("Sök: ");
                    String input = scanner.next();
                    if (input.equals("exit")) {
                        System.out.println("Shutting down...");
                        System.exit(35);
                    }
                    try (Connection connection = DriverManager.getConnection(url, user, password);
                         PreparedStatement statement = connection.prepareStatement("""
                                 SELECT barn.Namn, barn.Snäll
                                 FROM barn
                                 WHERE Namn = ?""")) {
                        statement.setString(1, input);
                        try (ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()) {
                                Barn b = new Barn(resultSet.getString(1), resultSet.getInt(2));
                                System.out.print(b.name() + ": ");
                                System.out.println(b.nice());
                            } else {
                                System.out.println(input + " finns inte i databasen.");
                            }
                        }
                    } catch (SQLException ex) {
                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " + ex.getErrorCode());
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

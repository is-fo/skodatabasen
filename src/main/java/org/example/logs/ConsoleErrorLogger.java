package org.example.logs;

import java.io.IOException;
import java.sql.SQLException;

public class ConsoleErrorLogger implements ErrorLogger {
    /**
     * <a href="https://docs.oracle.com/en/java/javase/17/language/pattern-matching-switch.html">pattern matching switch</a>
     *
     * @param ex the exception to parse
     */
    public void logError(Exception ex) {
        switch (ex) {
            case SQLException e -> {
                System.err.println("SQLException: " + e.getMessage());
                System.err.println("SQLState: " + e.getSQLState());
                System.err.println("VendorError: " + e.getErrorCode());
            }
            case IOException e -> {
                System.err.println("IOException: " + e.getMessage());
            }
            default -> System.err.println("Exception: " + ex.getMessage());
        }
        System.err.println("Line: " + ex.getStackTrace()[ex.getStackTrace().length - 1]); //plockar ut raden det hände på, kanske ska bytas ut med ett helt stacktrace
    }
}

package org.example.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {

    Scanner scanner;

    public UserInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getEmailFromUser() {
        System.out.print("Email: ");
        return scanner.nextLine();
    }

    public String getPasswordFromUser() {
        System.out.print("Password: ");
        return scanner.nextLine();
    }

    public int getMenuChoiceFromUser() {
        return scanner.nextInt();
    }

    public int getAntalFromUser(int max) {
        System.out.println("Hur många skor vill du ha? 1-" + max);
        int antal;
        while (true) {
            try {
                antal = scanner.nextInt();
                if (antal > max) {
                    System.out.println("Det finns inte så många skor, " + max + " skor är tillagda istället");
                    return max;
                }
                return antal;
            } catch (InputMismatchException e) {
                System.out.println("Skriv ett tal som inte är större än " + Integer.MAX_VALUE);
            } finally {
                resetScanner();
            }
        }
    }

    public boolean getYesNoFromUser() {
        String resp = scanner.nextLine();
        return resp.equals("1") || resp.equalsIgnoreCase("Y") || resp.equalsIgnoreCase("YES")
                || resp.equalsIgnoreCase("ja");
    }

    public void resetScanner() {
        scanner.nextLine();
    }
}

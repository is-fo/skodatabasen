package org.example;

import org.example.credentials.DatabaseCredentials;
import org.example.credentials.FileEnvCredentials;

public class Main {
    public static void main(String[] args) {
        DatabaseCredentials dbc = FileEnvCredentials.getInstance();
        dbc.getDatabaseUrl();
        dbc.getUsername();
        dbc.getPassword();
    }
}
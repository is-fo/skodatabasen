package org.example.credentials;

import org.example.logs.ConsoleErrorLogger;
import org.example.logs.ErrorLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileEnvCredentials implements DatabaseCredentials {

    private static String dbUrl, username;

    private static FileEnvCredentials instance;

    private FileEnvCredentials() {
        readProperties();
    }

    public static FileEnvCredentials getInstance() {
        if (instance == null) {
            instance = new FileEnvCredentials();
        }
        return instance;
    }

    private static void readProperties() {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/secret/secret.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            dbUrl = properties.getProperty("db.url");
            username = properties.getProperty("db.user");
        } catch (IOException e) {
            new ConsoleErrorLogger().logError(e);
        }
    }

    @Override
    public String getDatabaseUrl() {
        return dbUrl;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return System.getenv("pumper");
    }
}

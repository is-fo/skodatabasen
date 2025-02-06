package org.example.repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.credentials.DatabaseCredentials;
import org.example.credentials.FileEnvCredentials;

public class FilterTestMain {
    public static void main(String[] args) {
        DatabaseCredentials dbc = FileEnvCredentials.getInstance();
        try {
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL(dbc.getDatabaseUrl());
            ds.setUser(dbc.getUsername());
            ds.setPassword(dbc.getPassword());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

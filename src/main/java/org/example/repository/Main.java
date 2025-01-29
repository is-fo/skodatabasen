package org.example.repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.credentials.DatabaseCredentials;
import org.example.credentials.FileEnvCredentials;

public class Main {
    public static void main(String[] args) {
        DatabaseCredentials dbc = FileEnvCredentials.getInstance();

        try {
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL(dbc.getDatabaseUrl());
            ds.setUser(dbc.getUsername());
            ds.setPassword(dbc.getPassword());
            AdressRepository ar = new AdressRepository(ds);
            System.out.println(ar.find(1));
            CustomerRepository cr = new CustomerRepository(ds);
            System.out.println(cr.find(3));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
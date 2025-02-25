package org.example.repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.controller.Controller;
import org.example.credentials.DatabaseCredentials;
import org.example.credentials.FileEnvCredentials;
import org.example.data.Category;

public class Main {
    public static void main(String[] args) {
        DatabaseCredentials dbc = FileEnvCredentials.getInstance();
        try {
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL(dbc.getDatabaseUrl());
            ds.setUser(dbc.getUsername());
            ds.setPassword(dbc.getPassword());
//            AdressRepository ar = new AdressRepository(ds);
//            System.out.println(ar.find(1));
            CategoryRepository car = new CategoryRepository(ds);
            System.out.println(car.findAll());

            for (Category c : car.findAll()) {
                System.out.println(c.name());
            }

            CustomerRepository cr = new CustomerRepository(ds);
            System.out.println(cr.find(3));
            System.out.println(cr.findByEmail("dunderhonung@gmail.com"));
//            OrderDetailedRepository odr = new OrderDetailedRepository(ds);
//            System.out.println(odr.find(1));
//            OrderRepository or = new OrderRepository(ds);
//            System.out.println(or.find(1));
//            OutOfStockRepository oos = new OutOfStockRepository(ds);
//            System.out.println(oos.find(1));
//            ShoeDetailedRepository shr = new ShoeDetailedRepository(ds);
//            System.out.println(shr.find(1));
//            ShoeRepository sh = new ShoeRepository(ds);
//            System.out.println(sh.find(3));
            Controller c = new Controller(ds);

            // CALL AddToCart(4, 1, 1, NULL);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
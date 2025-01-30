package org.example.repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.credentials.DatabaseCredentials;
import org.example.credentials.FileEnvCredentials;
import org.example.data.Shoe;
import org.example.data.ShoeDetailed;
import org.example.filter.IdFilter;

import java.util.List;
import java.util.Optional;

public class FilterTestMain {
    public static void main(String[] args) {
        DatabaseCredentials dbc = FileEnvCredentials.getInstance();
        try {
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL(dbc.getDatabaseUrl());
            ds.setUser(dbc.getUsername());
            ds.setPassword(dbc.getPassword());
            ShoeRepository shoe = new ShoeRepository(ds);
            ShoeDetailedRepository sdr = new ShoeDetailedRepository(ds);
            List<ShoeDetailed> values = sdr.findAll().stream()
                    .flatMap(Optional::stream)
                    .toList();

            IdFilter<Shoe, ShoeDetailed> filter = new IdFilter<>();
            System.out.println(filter.join(shoe.find(1), values));

            IdFilter<ShoeDetailed, ShoeDetailed> wrongFilter = new IdFilter<>();
            System.out.println("Error test:");
            System.out.println(wrongFilter.join(sdr.find(1), values));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

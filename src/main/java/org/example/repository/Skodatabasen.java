package org.example.repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.controller.Controller;
import org.example.controller.UserInput;
import org.example.credentials.DatabaseCredentials;
import org.example.credentials.FileEnvCredentials;
import org.example.data.*;
import org.example.filter.InStockFilter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class Skodatabasen {
    public static void main(String[] args) {
        DatabaseCredentials dbc = FileEnvCredentials.getInstance();
        try {
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL(dbc.getDatabaseUrl());
            ds.setUser(dbc.getUsername());
            ds.setPassword(dbc.getPassword());

            Skodatabasen skodatabasen = new Skodatabasen();

            UserInput userInput = new UserInput(new Scanner(System.in));
            CustomerRepository customerRepository = new CustomerRepository(ds);
            Customer customer;
            while (true) {
                Optional<Customer> cust = customerRepository.findByEmail(userInput.getEmailFromUser());
                if (cust.isPresent()) {
                    customer = cust.get();
                    break;
                } else {
                    System.out.println("Finns inte i kundregistret, försök igen.");
                }
            }

            while (true) {
                if (customer.password().equals(userInput.getPasswordFromUser())) {
                    System.out.println("Hej " + customer.name() + "!");
                    break;
                } else {
                    System.out.println("Fel lösenord, försök igen.");
                }
            }

            Integer last_insert_id = 0;
            while (true) {
                CategoryRepository categoryRepository = new CategoryRepository(ds);
                Category category = skodatabasen.getMenuChoice(categoryRepository.findAll(), userInput);

                ShoeRepository shoeRepository = new ShoeRepository(ds);
                List<Shoe> shoes = shoeRepository.findShoesInCategory(category);
                Shoe shoe = skodatabasen.getMenuChoice(shoes, userInput);

                ShoeDetailedRepository shoeDetailedRepository = new ShoeDetailedRepository(ds);
                List<ShoeDetailed> shoeDetaileds = shoeDetailedRepository.findByShoe(shoe);
                shoeDetaileds = new InStockFilter().filter(shoeDetaileds);
                if (shoeDetaileds.isEmpty()) {
                    System.out.println("Finns inga skor i lager av den sorten :-(");
                    continue;
                }
                ShoeDetailed shoeDetailed = skodatabasen.getMenuChoice(shoeDetaileds, userInput);

                Controller controller = new Controller(ds);
                if (last_insert_id != 0) {
                    controller.addToCart(shoeDetailed.id(), userInput.getAntalFromUser(shoeDetailed.inStock()), customer.id(), last_insert_id);
                } else {
                    controller.addToCart(shoeDetailed.id(), userInput.getAntalFromUser(shoeDetailed.inStock()), customer.id());
                    OrderRepository orderRepository = new OrderRepository(ds);
                    last_insert_id = orderRepository.findLatestOrderForCustomer(customer.id());
                }

                System.out.println("Vill du köpa mer skor? y/n");
                if (!userInput.getYesNoFromUser()) {
                    System.out.println("Hejdå!");
                    break;
                } else {
                    System.out.println();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T extends Entity> T getMenuChoice(List<T> menuChoices, UserInput userInput) {

        while (true) {
            for (int i = 0; i < menuChoices.size(); i++) {
                System.out.print("[" + (i + 1) + "] ");
                switch (menuChoices.get(i)) {
                    case Category c ->
                            System.out.print(c.name());
                    case Shoe s ->
                            System.out.print(s.brand() + " " + s.color() + " " + s.pris() + " kr");
                    case ShoeDetailed sd ->
                            System.out.print("Storlek: " + sd.size() + " Lagersaldo: " + sd.inStock());
                    default ->
                            throw new RuntimeException("Unsupported entity: " + menuChoices.get(i).getClass().getSimpleName());
                }
                if (i != menuChoices.size() - 1) {
                    System.out.print(", ");
                }
            }
            try {
                int menuChoice = userInput.getMenuChoiceFromUser();
                if (menuChoice > menuChoices.size() || menuChoice < 1) {
                    throw new IllegalArgumentException();
                }
                return menuChoices.get(menuChoice - 1);
            } catch (NoSuchElementException | IllegalArgumentException e) {
                System.out.println("Välj ett menyval.");
                userInput.resetScanner();
            }
        }
    }
}

package org.example.onlineshopclient;

import org.example.onlineshopclient.model.entity.User;
import org.example.onlineshopclient.service.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineShopClientApplication implements CommandLineRunner {

    @Autowired
    private UserServiceClient userServiceClient;

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userServiceClient.fetchAllUsers();

        User newUser = new User();
        newUser.setId(3L);
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setUsername("johndoe");
        newUser.setEmail("john.doe@example.com");
        newUser.setPassword("password123");

        System.out.println("Creating new user:");
        userServiceClient.createUser(newUser);

        // Примерен потребител за актуализиране
        User updatedUser = new User();
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Doe");
        updatedUser.setUsername("janedoe");
        updatedUser.setEmail("jane.doe@example.com");

        System.out.println("Modifying user with ID 2:");
        userServiceClient.modifyUser(2L, updatedUser);

        System.out.println("Removing user with ID 3:");
        userServiceClient.removeUser(3L);
    }
}

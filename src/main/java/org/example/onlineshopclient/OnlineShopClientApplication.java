package org.example.onlineshopclient;

import org.example.onlineshopclient.model.dto.UserDTO;
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
        System.out.println("=== Fetching All Users ===");
        userServiceClient.fetchAllUsers();

        UserDTO newUser = new UserDTO();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setUsername("johndoe");
        newUser.setEmail("john.doe@example.com");
        newUser.setPassword("password123");

        System.out.println("\n=== Creating New User ===");
        userServiceClient.createUser(newUser);

        UserDTO updatedUser = new UserDTO();
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Doe");
        updatedUser.setUsername("janedoe123");
        updatedUser.setEmail("jane.doe@example.com");

        System.out.println("\n=== Modifying User with ID 2 ===");
        userServiceClient.modifyUser(2L, updatedUser);

        System.out.println("\n=== Removing User with ID 3 ===");
        userServiceClient.removeUser(3L);
    }
}
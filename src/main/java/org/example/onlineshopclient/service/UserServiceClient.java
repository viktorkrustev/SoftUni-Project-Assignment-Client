package org.example.onlineshopclient.service;

import org.example.onlineshopclient.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public UserServiceClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public List<UserDTO> fetchAllUsers() {
        System.out.println("Fetching all users from: " + baseUrl + "/users");
        UserDTO[] userDTOs = restTemplate.getForObject(baseUrl + "/users", UserDTO[].class);

        if (userDTOs != null && userDTOs.length > 0) {
            System.out.println("Received " + userDTOs.length + " users:");
            for (UserDTO user : userDTOs) {
                printUserDetails(user);
            }
        } else {
            System.out.println("No users found.");
        }

        return Arrays.asList(userDTOs);
    }

    public UserDTO createUser(UserDTO userDTO) {
        System.out.println("Creating user at: " + baseUrl + "/users");
        System.out.println("UserDTO to be sent: " + userDTO.getUsername());

        UserDTO createdUserDTO = restTemplate.postForObject(baseUrl + "/users", userDTO, UserDTO.class);

        if (createdUserDTO != null) {
            System.out.println("User created successfully:");
            printUserDetails(createdUserDTO);
        } else {
            System.out.println("Failed to create user.");
        }

        return createdUserDTO;
    }

    public void modifyUser(Long id, UserDTO userDTO) {
        System.out.println("Updating user with ID " + id + " at: " + baseUrl + "/users/" + id);
        System.out.println("UserDTO to be updated: " + userDTO.getUsername());

        restTemplate.put(baseUrl + "/users/" + id, userDTO);

        System.out.println("User with ID " + id + " has been updated successfully.");
    }




    public void removeUser(Long id) {
        System.out.println("Deleting user with ID " + id + " at: " + baseUrl + "/users/" + id);
        restTemplate.delete(baseUrl + "/users/" + id);
        System.out.println("User with ID " + id + " has been deleted successfully.");
    }

    private void printUserDetails(UserDTO userDTO) {
        System.out.println("User ID: " + userDTO.getId());
        System.out.println("First Name: " + userDTO.getFirstName());
        System.out.println("Last Name: " + userDTO.getLastName());
        System.out.println("Username: " + userDTO.getUsername());
        System.out.println("Email: " + userDTO.getEmail());
        System.out.println("--------------");
    }
}
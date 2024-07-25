package org.example.onlineshopclient.service;

import org.example.onlineshopclient.UserRestClient;
import org.example.onlineshopclient.model.dto.UserDTO;
import org.example.onlineshopclient.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceClient {

    private final UserRestClient userRestClient;

    @Autowired
    public UserServiceClient(UserRestClient userRestClient) {
        this.userRestClient = userRestClient;
    }

    public List<User> fetchAllUsers() {
        List<UserDTO> userDTOs = userRestClient.getAllUsers();
        List<User> users = userDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        // Показване на потребителите на конзолата
        users.forEach(user -> {
            System.out.println("User ID: " + user.getId());
            System.out.println("First Name: " + user.getFirstName());
            System.out.println("Last Name: " + user.getLastName());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("--------------");
        });

        return users;
    }

    public UserDTO createUser(User user) {
        UserDTO userDTO = convertToDTO(user);
        UserDTO createdUserDTO = userRestClient.addUser(userDTO);

        if (createdUserDTO == null) {
            System.out.println("Error: Created UserDTO is null");
            return null;
        }

        User createdUser = convertToEntity(createdUserDTO);

        // Показване на създадения потребител на конзолата
        System.out.println("Created User ID: " + createdUser.getId());
        System.out.println("First Name: " + createdUser.getFirstName());
        System.out.println("Last Name: " + createdUser.getLastName());
        System.out.println("Username: " + createdUser.getUsername());
        System.out.println("Email: " + createdUser.getEmail());
        System.out.println("--------------");

        return createdUserDTO;
    }

    public UserDTO modifyUser(Long id, User user) {
        UserDTO userDTO = convertToDTO(user);
        UserDTO updatedUserDTO = userRestClient.updateUser(id, userDTO);

        if (updatedUserDTO == null) {
            System.out.println("Error: Updated UserDTO is null");
            return null;
        }

        User updatedUser = convertToEntity(updatedUserDTO);

        // Показване на актуализирания потребител на конзолата
        System.out.println("Updated User ID: " + updatedUser.getId());
        System.out.println("First Name: " + updatedUser.getFirstName());
        System.out.println("Last Name: " + updatedUser.getLastName());
        System.out.println("Username: " + updatedUser.getUsername());
        System.out.println("Email: " + updatedUser.getEmail());
        System.out.println("--------------");

        return updatedUserDTO;
    }

    public void removeUser(Long id) {
        userRestClient.deleteUser(id);

        // Показване на изтрития потребител на конзолата
        System.out.println("Deleted User ID: " + id);
        System.out.println("--------------");
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}

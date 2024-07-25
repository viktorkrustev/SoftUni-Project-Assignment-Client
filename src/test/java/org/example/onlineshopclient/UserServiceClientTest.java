package org.example.onlineshopclient;

import org.example.onlineshopclient.model.dto.UserDTO;
import org.example.onlineshopclient.model.entity.User;
import org.example.onlineshopclient.service.UserServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceClientTest {

    @Mock
    private UserRestClient userRestClient;

    @InjectMocks
    private UserServiceClient userServiceClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAllUsers() {
        List<UserDTO> userDTOs = Arrays.asList(new UserDTO(), new UserDTO());
        when(userRestClient.getAllUsers()).thenReturn(userDTOs);

        List<User> users = userServiceClient.fetchAllUsers();

        assertEquals(userDTOs.size(), users.size());
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("john.doe@example.com");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setUsername("johndoe");
        userDTO.setEmail("john.doe@example.com");

        when(userRestClient.addUser(any(UserDTO.class))).thenReturn(userDTO);

        UserDTO createdUserDTO = userServiceClient.createUser(user);

        assertEquals(userDTO, createdUserDTO);
    }

    @Test
    public void testModifyUser() {
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setUsername("janedoe");
        user.setEmail("jane.doe@example.com");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("Jane");
        userDTO.setLastName("Doe");
        userDTO.setUsername("janedoe");
        userDTO.setEmail("jane.doe@example.com");

        when(userRestClient.updateUser(eq(1L), any(UserDTO.class))).thenReturn(userDTO);

        UserDTO updatedUserDTO = userServiceClient.modifyUser(1L, user);

        assertEquals(userDTO, updatedUserDTO);
    }

    @Test
    public void testRemoveUser() {
        // Create and add a user
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("john.doe@example.com");

        userServiceClient.createUser(user);

        // Remove the user
        userServiceClient.removeUser(1L);

        // Verify that the delete method on userRestClient was called with the correct ID
        verify(userRestClient, times(1)).deleteUser(1L);
    }
}

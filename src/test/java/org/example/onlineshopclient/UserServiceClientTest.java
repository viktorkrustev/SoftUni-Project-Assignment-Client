package org.example.onlineshopclient;

import org.example.onlineshopclient.model.dto.UserDTO;
import org.example.onlineshopclient.service.UserServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserServiceClient userServiceClient;

    private final String baseUrl = "http://localhost:8080/api";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userServiceClient = new UserServiceClient(restTemplate, baseUrl);
    }

    @Test
    public void testFetchAllUsers() {
        UserDTO user1 = new UserDTO(1L, "John", "Doe", "john_doe", "john@example.com");
        UserDTO user2 = new UserDTO(2L, "Jane", "Doe", "jane_doe", "jane@example.com");
        UserDTO[] users = {user1, user2};

        when(restTemplate.getForObject(baseUrl + "/users", UserDTO[].class)).thenReturn(users);

        List<UserDTO> userList = userServiceClient.fetchAllUsers();

        verify(restTemplate, times(1)).getForObject(baseUrl + "/users", UserDTO[].class);
        assertNotNull(userList);
        assertEquals(2, userList.size());
        assertEquals("john_doe", userList.get(0).getUsername());
    }

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", "john_doe", "john@example.com");

        when(restTemplate.postForObject(baseUrl + "/users", userDTO, UserDTO.class)).thenReturn(userDTO);

        UserDTO createdUser = userServiceClient.createUser(userDTO);

        verify(restTemplate, times(1)).postForObject(baseUrl + "/users", userDTO, UserDTO.class);
        assertNotNull(createdUser);
        assertEquals("john_doe", createdUser.getUsername());
    }

    @Test
    public void testModifyUser() {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", "john_doe", "john@example.com");

        doNothing().when(restTemplate).put(baseUrl + "/users/1", userDTO);

        userServiceClient.modifyUser(1L, userDTO);

        verify(restTemplate, times(1)).put(baseUrl + "/users/1", userDTO);
    }

    @Test
    public void testRemoveUser() {
        doNothing().when(restTemplate).delete(baseUrl + "/users/1");

        userServiceClient.removeUser(1L);

        verify(restTemplate, times(1)).delete(baseUrl + "/users/1");
    }
}

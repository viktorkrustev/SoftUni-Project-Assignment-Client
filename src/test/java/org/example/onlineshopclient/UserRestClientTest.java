package org.example.onlineshopclient;

import org.example.onlineshopclient.config.AppConfig;
import org.example.onlineshopclient.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserRestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AppConfig appConfig;

    @InjectMocks
    private UserRestClient userRestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<UserDTO> expectedUsers = Arrays.asList(new UserDTO(), new UserDTO());
        when(appConfig.getUrl()).thenReturn("http://localhost:8080/api");
        when(restTemplate.exchange(
                eq("http://localhost:8080/api/users"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(ResponseEntity.ok(expectedUsers));

        List<UserDTO> actualUsers = userRestClient.getAllUsers();

        assertEquals(expectedUsers.size(), actualUsers.size());
    }

    @Test
    public void testAddUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(2L);
        userDTO.setEmail("email@abv.bg");
        userDTO.setFirstName("Test");
        userDTO.setLastName("Testov");
        userDTO.setUsername("test123");
        when(appConfig.getUrl()).thenReturn("http://localhost:8080/api");
        when(restTemplate.postForObject(anyString(), any(UserDTO.class), eq(UserDTO.class)))
                .thenReturn(userDTO);

        UserDTO createdUser = userRestClient.addUser(userDTO);

        assertEquals(userDTO, createdUser);
    }

    @Test
    public void testUpdateUser() {
        UserDTO userDTO = new UserDTO();
        when(appConfig.getUrl()).thenReturn("http://localhost:8080/api");

        UserDTO updatedUser = userRestClient.updateUser(1L, userDTO);

        assertEquals(userDTO, updatedUser);
    }

    @Test
    public void testDeleteUser() {
        when(appConfig.getUrl()).thenReturn("http://localhost:8080/api");

        userRestClient.deleteUser(1L);

        verify(restTemplate, times(1)).delete("http://localhost:8080/api/users/1");
    }
}

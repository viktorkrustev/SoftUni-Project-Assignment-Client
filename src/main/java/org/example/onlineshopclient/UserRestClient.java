package org.example.onlineshopclient;

import org.example.onlineshopclient.config.AppConfig;
import org.example.onlineshopclient.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserRestClient {
    private final RestTemplate restTemplate;
    private final AppConfig appConfig;

    @Autowired
    public UserRestClient(RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
    }

    public List<UserDTO> getAllUsers() {
        String url = appConfig.getUrl() + "/users";
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );
        return response.getBody();
    }

    public UserDTO addUser(UserDTO userDTO) {
        String url = appConfig.getUrl() + "/users";
        System.out.println("Sending request to URL: " + url);
        System.out.println("UserDTO to be sent: " + userDTO);

        try {
            UserDTO response = restTemplate.postForObject(url, userDTO, UserDTO.class);
            if (response == null) {
                System.out.println("Received null response from addUser");
            } else {
                System.out.println("Received response from addUser: " + response);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while adding user", e);
        }
    }




    public UserDTO updateUser(Long id, UserDTO userDTO) {
        String url = appConfig.getUrl() + "/users/" + id;
        restTemplate.put(url, userDTO);
        return userDTO; // Възвръщаме DTO-то след актуализацията
    }


    public void deleteUser(Long id) {
        String url = appConfig.getUrl() + "/users/" + id;
        restTemplate.delete(url);
    }
}

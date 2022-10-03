package com.example.demo.consumption;

import com.example.demo.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RESTAPIConsumer {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    RestTemplate rest = new RestTemplate();

    public User getUserByID(Integer id) {

        ResponseEntity<User> responseEntity =
                rest.getForEntity("http://localhost:8080/users/{id}",
                        User.class, id);

         return responseEntity.getBody();
    }

    public void updateUser(User user) {
        rest.put("http://localhost:8080/users/{id}",
                user,
                user.getID());
    }

    public void deleteUser(User user) {
        rest.delete("http://localhost:8080/users/{id}",
                user.getID());
    }

    public User createUser(User user) {
        return rest.postForObject("http://localhost:8080/users",
                user,
                User.class);
    }
}

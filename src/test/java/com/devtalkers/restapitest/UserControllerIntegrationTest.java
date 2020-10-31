package com.devtalkers.restapitest;

import com.devtalkers.restapitest.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("/test.sql")
    public void getUserByIdTest() {

        ResponseEntity<User> response = testRestTemplate.getForEntity("/user/1001", User.class);

        assertEquals(1001, response.getBody().getId());
        assertEquals("John", response.getBody().getName());
        assertEquals("john@devt.com", response.getBody().getEmail());
        assertEquals("4564654", response.getBody().getPhone());
    }

    @Test
    public void saveUser(){

        User user = new User();

        user.setName("William");
        user.setEmail("william@devt.com");
        user.setPhone("45665468");
        user.setGender("Male");

        HttpEntity<User> request = new HttpEntity<>(user);

        ResponseEntity<User> response = testRestTemplate.postForEntity("/user", request, User.class);

        assertNotNull(response.getBody().getId());
        assertEquals("William", response.getBody().getName());
        assertEquals("william@devt.com", response.getBody().getEmail());
        assertEquals("45665468", response.getBody().getPhone());
        assertEquals("Male", response.getBody().getGender());
    }


}

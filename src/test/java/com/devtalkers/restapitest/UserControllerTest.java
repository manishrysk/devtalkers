package com.devtalkers.restapitest;

import com.devtalkers.restapitest.controllers.UserController;
import com.devtalkers.restapitest.entity.User;
import com.devtalkers.restapitest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getUserByIdTest() throws Exception {

        //mock the data return by the user service class
        User user = new User();

        user.setName("John");
        user.setEmail("John@devt.com");
        user.setPhone("965852262");
        user.setGender("Male");

        when(userService.getUserById(anyInt())).thenReturn(user);
        //create a mock HTTP request to verify the expected result

        mockMvc.perform(MockMvcRequestBuilders.get("/user/12"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("John@devt.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("965852262"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"))
                .andExpect(status().isOk());

    }

    @Test
    public void saveUserTest() throws Exception {

        //mock the user data that we have to save
        User user = new User();
        user.setId(1);
        user.setName("John");
        user.setEmail("John@devt.com");
        user.setPhone("965852262");
        user.setGender("Male");

        when(userService.saveUser(any(User.class))).thenReturn(user);

        //mock request "/user"

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("John@devt.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("965852262"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"));

    }

}

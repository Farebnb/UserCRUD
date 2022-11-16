package com.cognizant.usercrud;

import com.cognizant.usercrud.models.User;
import com.cognizant.usercrud.repo.UserRepo;
import com.cognizant.usercrud.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = UsercrudApplication.class)
@AutoConfigureMockMvc
public class UserServiceTests {

    @MockBean
    UserRepo ur;

    @Autowired
    UserService us;

    @Test
    public void registerTest() {
        us = new UserService(ur);
        User testUser = new User(1010, "Mohamed", "Abdulla", "mabdulla", "mohamed@gmail.com", "password", 0);
        us.register("Mohamed", "Abdulla", "mabdulla", "mohamed@gmail.com", "password");
        when((ur).findById(anyInt())).thenReturn(testUser);
        User result = us.getById(1010);
        assertEquals("mabdulla", result.getUsername(), "pass test");
    }

    @Test
    public void getByUsernameTest() {
        us = new UserService(ur);
        User testUser = new User(1, "John", "Smith", "jsmith", "john.smith@gmail.com", "password", 3);
        when((ur).findByUsername(any())).thenReturn(testUser);
        User result = us.getByUsername("jsmith");
        assertEquals("jsmith", result.getUsername(), "pass test");

    }

    @Test
    public void getByIdTest() {
        us = new UserService(ur);
        User testUser = new User(1, "John", "Smith", "jsmith", "john.smith@gmail.com", "password", 3);
        when((ur).findById(anyInt())).thenReturn(testUser);
        User result = us.getById(1);
        assertEquals(1, result.getId(), "pass test");

    }

    @Test
    public void getByListingIdTest() {
        us = new UserService(ur);
        User testUser = new User(1, "John", "Smith", "jsmith", "john.smith@gmail.com", "password", 3);
        when((ur).findByListingId(anyInt())).thenReturn(testUser);
        User result = us.getByListingId(3);
        assertEquals(3, result.getListingId(), "pass test");

    }

    @Test
    public void getAllTest() {
        us = new UserService(ur);
        List<User> userList = new ArrayList<>();
        User testUser = new User(1, "John", "Smith", "jsmith", "john.smith@gmail.com", "password", 3);
        userList.add(testUser);
        when((ur).findAll()).thenReturn(userList);
        List<User> result = us.getAll();
        assertEquals(testUser.getId(), result.get(0).getId(), "pass test");

    }

    @Test
    public void updateTest() {
        us = new UserService(ur);
        User testUser = new User(1, "John", "Smith", "jsmith", "john.smith@gmail.com", "password", 3);
        User updatedUser = new User(1, "Joe", "Smith", "jsmith", "john.smith@gmail.com", "password", 3);
        when((ur).saveAndFlush(any())).thenReturn(testUser);
        updatedUser = us.update(testUser);
        assertEquals("John", updatedUser.getFirstname(), "pass test");
    }

    @Test
    public void deleteTest() {
        us = new UserService(ur);
        doNothing().when(ur).deleteById(anyInt());
        us.delete(0);
        verify(ur).deleteById(anyInt());
    }

}

package com.cognizant.usercrud;

import com.cognizant.usercrud.models.User;
import com.cognizant.usercrud.repo.UserRepo;
import com.cognizant.usercrud.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService us;

    @Autowired
    private UserRepo ur;

    private ObjectMapper om = new ObjectMapper();

    @Test
    @Transactional
    public void registerTest() throws Exception {

        LinkedHashMap<String, String> registerBody = new LinkedHashMap<>();

        registerBody.put("firstname", "John");
        registerBody.put("lastname", "Smith");
        registerBody.put("username", "jsmith");
        registerBody.put("email", "jsmith@gmail.com");
        registerBody.put("password", "password");

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(registerBody))
                )
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Smith"))
                .andExpect(jsonPath("$.username").value("jsmith"))
                .andExpect(jsonPath("$.email").value("jsmith@gmail.com"))
                .andExpect(jsonPath("$.password").value("password"));

    }

    @Test
    @Transactional
    public void getByIdTest() throws Exception {

        mockMvc.perform(get("/user/?id=1000"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1000));

    }

    @Test
    @Transactional
    public void getByUsernameTest() throws Exception {

        mockMvc.perform(get("/user/username/?username=jsmith"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("jsmith"));
    }

    @Test
    @Transactional
    public void getByListingIdTest() throws Exception {

        mockMvc.perform(get("/user/listingId/?listingId=3"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.listingId").value(3));
    }

    @Test
    @Transactional
    public void getAllTest() throws Exception {

        mockMvc.perform(get("/user/all"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    @Transactional
    public void updateTest() throws Exception {
        User u = ur.save(new User(0, "John", "Smith", "jsmith", "jsmith@gmail.com", "password", 0));

        User updated = new User(0, "NewJohn", "NewSmith", "Newjsmith", "Newjsmith@gmail.com", "Newpassword", 0);

        mockMvc.perform(put("/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(updated))
                )
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    @Transactional
    public void deleteTest() throws Exception {
        User u = ur.save(new User(1, "John", "Smith", "jsmith", "jsmith@gmail.com", "password", 0));

        mockMvc.perform(delete("/user/delete/?id=1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(u))
                )
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    @Transactional
    public void unsuccessfulDeleteTest() throws Exception {
        User u = ur.save(new User(1, "John", "Smith", "jsmith", "jsmith@gmail.com", "password", 0));

        mockMvc.perform(delete("/user/delete/?id=2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(u))
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}

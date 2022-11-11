package com.cognizant.usercrud;

import com.cognizant.usercrud.repo.UserRepo;
import com.cognizant.usercrud.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

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
    public void getByIdTest() throws Exception {

        mockMvc.perform(get("/user/?id=1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1));
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

}

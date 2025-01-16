package polish_community_group_11.polish_community.profile.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import polish_community_group_11.polish_community.register.models.User;
import polish_community_group_11.polish_community.register.services.UserService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment. RANDOM_PORT)
@AutoConfigureMockMvc
class ProfileControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    @WithUserDetails("user@email.com")
    void whenUserCallsProfileEndpointThenTheirProfileIsReturned() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/profile-json")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json("{\"email\": \"user@email.com\"}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"bio\": \"Jane's bio\"}"));
    }
}





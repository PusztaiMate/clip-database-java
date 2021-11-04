package hu.pumate.clipdb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ClipControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void addNewClip() throws Exception{
        mvc.perform(post("/api/v1/clips")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"this_is_the_url\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.url").value("this_is_the_url"));
    }
}

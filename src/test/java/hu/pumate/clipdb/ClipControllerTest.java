package hu.pumate.clipdb;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ClipControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void addNewClip() throws Exception{
        Long id = 1L;
        mvc.perform(post("/api/v1/clips")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"this_is_the_url\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.url").value("this_is_the_url"));

        deleteClip(id);
    }

    @Test
    public void getClip() throws Exception {
        String url = "this://is.url";
        Long id = postClip(url);

        mvc.perform(get(String.format("/api/v1/clips/%d", id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.url").value(url));

        deleteClip(id);
    }

    @Test
    public void updateClip() throws Exception {
        String oldUrl = "old.url";
        String newUrl = "new.url";
        Long id = postClip(oldUrl);
        String payload = String.format("{\"url\": \"%s\", \"id\": \"%d\"}", newUrl, id);

        mvc.perform(put(String.format("/api/v1/clips/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllClips() throws Exception{
        String url1 = "this_is_uri_1";
        String url2 = "this_is_uri_2";
        Long id1 = postClip(url1);
        Long id2 = postClip(url2);

        mvc.perform(get("/api/v1/clips"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].url").value(url1))
                .andExpect(jsonPath("$[1].url").value(url2))
                .andExpect(jsonPath("$[2]").doesNotExist());

        // cleanup
        deleteClip(id1);
        deleteClip(id2);
    }

    private Long postClip(String url) throws Exception {
        MvcResult res =  mvc.perform(post("/api/v1/clips")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"url\": \"%s\"}", url)))
                .andExpect(status().isCreated()).andReturn();
        int id = JsonPath.read(res.getResponse().getContentAsString(), "$.id");
        return (long) id;
    }


    private void deleteClip(Long id) throws Exception {
        mvc.perform(delete(String.format("/api/v1/clips/%d", id)))
                .andExpect(status().isNoContent());
    }
}

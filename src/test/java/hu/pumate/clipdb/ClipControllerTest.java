package hu.pumate.clipdb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import hu.pumate.clipdb.controller.apidescriptiors.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class ClipControllerTest {

    @Autowired
    private MockMvc mvc;

    Random random;
    private ClipApiV1 api;
    private ObjectMapper mapper;


    private String url;
    private String startTime;
    private String endTime;
    private String subject;
    private List<String> tags;

    @BeforeAll
    public void setupBeforeAll() {
        this.mapper = new ObjectMapper();
        api = new ClipApiV1(mvc, this.mapper);
    }

    @BeforeEach
    public void setup() {
        url = "url";
        startTime = "01:10";
        endTime = "02:20";
        subject = "subject";
        tags = List.of("tag1", "tag2");
        this.random = new Random(System.currentTimeMillis());
    }

    @Test
    public void addNewClip() throws Exception{

        var requestDTO = makeDefaultCreateRequestDTOV1();

        var response = api.post(requestDTO)
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        var responseDTO = parseCreateResponseToDTO(response);
        assertTrue(responseDTO.getId() > 0);
    }

    @Test
    public void getClip() throws Exception {
        var request = makeDefaultCreateRequestDTOV1();
        var id = getIdFromCreateResponse(api.post(request).andReturn().getResponse().getContentAsString());

        var response = api.get(id)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        var responseDTO = parseGetResponseToDTO(response);
        var expectedDTO = new GetClipResponseDTOV1(0L, url, startTime, endTime, tags, subject);
        assertEqualsExceptId(responseDTO, expectedDTO);
    }

    @Test
    public void putCreatesNewClipIfItsNotPresentAlready() throws Exception {
        var id = getRandomClipId();
        api.get(id).andExpect(status().isNotFound());
        var newClip = makeDefaultUpdateRequestDTOV1();

        var res = api.put(id, newClip);

        res.andExpect(status().isOk());
    }

    @Test
    public void putUpdatesAlreadyPresentClip() throws Exception {
        var createClipDTO = makeDefaultCreateRequestDTOV1();
        var resCreate = api.post(createClipDTO).andExpect(status().isCreated());

        var id = getIdFromCreateResponse(resCreate.andReturn().getResponse().getContentAsString());
        var oldUrl = createClipDTO.getUrl();
        var newUrl = oldUrl + "_updated";
        var updateClipDTO = new UpdateClipRequestDTOV1(newUrl, createClipDTO.getStartTime(), createClipDTO.getEndTime(), createClipDTO.getTags(), createClipDTO.getSubject());

        var resUpdate = api.put(id, updateClipDTO);
        resUpdate.andExpect(status().isOk());
        var responseBody = resUpdate.andReturn().getResponse().getContentAsString();
        this.mapper.readValue(responseBody, UpdateClipResponseDTOV1.class);
    }

    @Test
    public void getAllClips() throws Exception{
        var createRequestDTO1 = makeDefaultCreateRequestDTOV1(getTimeStamp());
        api.post(createRequestDTO1);
        var createRequestDTO2 = makeDefaultCreateRequestDTOV1(getTimeStamp());
        api.post(createRequestDTO2);

        var response = mvc.perform(get("/api/v1/clips"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        var responseDTO = parseGetAllClipsReponse(response);
        assertTrue(responseDTO.size() >= 2);
        List<String> urls = responseDTO.stream().map(GetClipResponseDTOV1::getUrl).toList();
        assertTrue(urls.contains(createRequestDTO1.getUrl()));
        assertTrue(urls.contains(createRequestDTO2.getUrl()));
    }

    private Long getRandomClipId() {
        var offset = 1000L;
        return offset + random.nextInt(99999);
    }

    private CreateNewClipRequestDTOV1 makeDefaultCreateRequestDTOV1() {
        return new CreateNewClipRequestDTOV1(url, startTime, endTime, tags, subject);
    };

    private CreateNewClipRequestDTOV1 makeDefaultCreateRequestDTOV1(String postfix) {
        url += String.format("_%s", postfix);
        startTime += String.format("_%s", postfix);
        endTime += String.format("_%s", postfix);
        subject += String.format("_%s", postfix);
        for (String tag : tags) {
            tag += String.format("_%s", postfix);
        }

        return new CreateNewClipRequestDTOV1(url, startTime, endTime, tags, subject);
    };

    private UpdateClipRequestDTOV1 makeDefaultUpdateRequestDTOV1() {
        return new UpdateClipRequestDTOV1(url, startTime, endTime, tags, subject);
    }

    private Long getIdFromCreateResponse(String response) throws JsonProcessingException {
        return this.mapper.readValue(response, CreateNewClipResponseDTOV1.class).getId();
    }

    private GetClipResponseDTOV1 parseGetResponseToDTO(String response) throws JsonProcessingException {
        return this.mapper.readValue(response, GetClipResponseDTOV1.class);
    }

    private CreateNewClipResponseDTOV1 parseCreateResponseToDTO(String response) throws JsonProcessingException {
        return this.mapper.readValue(response, CreateNewClipResponseDTOV1.class);
    }

    private List<GetClipResponseDTOV1> parseGetAllClipsReponse(String response) throws JsonProcessingException {
        return this.mapper.readValue(response, new TypeReference<List<GetClipResponseDTOV1>>() {});
    }

    private void assertEqualsExceptId(GetClipResponseDTOV1 first, GetClipResponseDTOV1 second) {
        assertEquals(first.getUrl(), second.getUrl());
        assertEquals(first.getStartTime(), second.getStartTime());
        assertEquals(first.getEndTime(), second.getEndTime());
        assertEquals(first.getSubject(), second.getSubject());
        assertEquals(first.getTags(), second.getTags());
    }

    private String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }
}

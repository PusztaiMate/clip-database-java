package hu.pumate.clipdb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.pumate.clipdb.controller.apidescriptiors.CreateNewClipRequestDTOV1;
import hu.pumate.clipdb.controller.apidescriptiors.UpdateClipRequestDTOV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ClipApiV1 {

    private final ObjectMapper mapper;

    private final MockMvc mvc;

    public ClipApiV1(MockMvc mvc, ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    public ResultActions get(Long clipId) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(String.format("/api/v1/clips/%d", clipId)));
    }

    public ResultActions put(Long clipId, UpdateClipRequestDTOV1 requestDTO) throws Exception {
        var payload = objectToJsonString(requestDTO);
        return mvc.perform(MockMvcRequestBuilders.put(String.format("/api/v1/clips/%d", clipId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload));
    }

    public ResultActions post(CreateNewClipRequestDTOV1 requestDTO) throws Exception {
        var payload = objectToJsonString(requestDTO);
        return mvc.perform(MockMvcRequestBuilders.post("/api/v1/clips")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload));
    }

    private String objectToJsonString(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new ClipApiException("could not convert object to json");
        }
    }
}

package hu.pumate.clipdb.controller;

import hu.pumate.clipdb.controller.apidescriptiors.*;
import hu.pumate.clipdb.entity.Clip;
import hu.pumate.clipdb.exception.ClipNotFoundException;
import hu.pumate.clipdb.service.ClipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClipController {

    @Autowired
    private ClipService clipService;

    @GetMapping("/api/v1/clips")
    public ResponseEntity<List<GetClipResponseDTOV1>> all() {
        List<Clip> clips = clipService.getAllClips();
        List<GetClipResponseDTOV1> clipDTOs = new ArrayList<>();
        clips.stream().map(this::makeGetClipResponseDTOFromClip).forEach(clipDTOs::add);
        return new ResponseEntity<>(clipDTOs, HttpStatus.OK);
    }

    @PostMapping("/api/v1/clips")
    public ResponseEntity<CreateNewClipResponseDTOV1> newClip(@RequestBody CreateNewClipRequestDTOV1 requestDTO) {
        var clip = makeClipFromRequestApiDTO(requestDTO);
        Clip c = clipService.addNewClip(clip);
        var response = makeCreateClipResponseDTOFromClip(c);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/clips/{id}")
    public ResponseEntity<GetClipResponseDTOV1> getOne(@PathVariable Long id) {
        Clip clip = clipService.getClip(id).orElseThrow(() -> new ClipNotFoundException(id));
        var response = makeGetClipResponseDTOFromClip(clip);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/v1/clips/{id}")
    public ResponseEntity<UpdateClipResponseDTOV1> updateClip(@RequestBody UpdateClipRequestDTOV1 newClipRequest, @PathVariable Long id) {
        var newClip = makeClipFromUpdateRequestApiDTO(newClipRequest);
        Clip clip = clipService.updateClip(newClip, id);
        var responseDTO = makeUpdateClipResponseDTOFromClip(clip);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/clips/{id}")
    public ResponseEntity<?> deleteClip(@PathVariable Long id) {
        clipService.deleteClip(id);
        return ResponseEntity.noContent().build();
    }

    private Clip makeClip(String url, String startTime, String endTime, String subject, List<String> tags) {
        var clip = new Clip();
        clip.setUrl(url);
        clip.setStartTime(startTime);
        clip.setEndTime(endTime);
        clip.setSubject(subject);
        clip.setTags(tags);
        return clip;
    }

    private Clip makeClipFromRequestApiDTO(CreateNewClipRequestDTOV1 dto) {
        return makeClip(dto.getUrl(), dto.getStartTime(), dto.getEndTime(), dto.getSubject(), dto.getTags());
    }

    private Clip makeClipFromUpdateRequestApiDTO(UpdateClipRequestDTOV1 dto) {
        return makeClip(dto.getUrl(), dto.getStartTime(), dto.getEndTime(), dto.getSubject(), dto.getTags());
    }


    private CreateNewClipResponseDTOV1 makeClipResponseDTO(Long id) {
        var clipResponse = new CreateNewClipResponseDTOV1();
        clipResponse.setId(id);
        return clipResponse;
    }

    private CreateNewClipResponseDTOV1 makeCreateClipResponseDTOFromClip(Clip clip) {
        return makeClipResponseDTO(clip.getId());
    }

    private UpdateClipResponseDTOV1 makeUpdateClipResponseDTO(Long id, String url, String startTime, String endTime, String subject, List<String> tags) {
        var dto = new UpdateClipResponseDTOV1();
        dto.setId(id);
        dto.setUrl(url);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setSubject(subject);
        dto.setTags(tags);
        return dto;
    }

    private UpdateClipResponseDTOV1 makeUpdateClipResponseDTOFromClip(Clip c) {
        return makeUpdateClipResponseDTO(c.getId(), c.getUrl(), c.getStartTime(), c.getEndTime(), c.getSubject(), c.getTags());
    }

    private GetClipResponseDTOV1 makeGetClipResponseDTO(Long id, String url, String startTime, String endTime, String subject, List<String> tags) {
        var dto = new GetClipResponseDTOV1();
        dto.setId(id);
        dto.setUrl(url);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setSubject(subject);
        dto.setTags(tags);
        return dto;
    }

    private GetClipResponseDTOV1 makeGetClipResponseDTOFromClip(Clip c) {
        return makeGetClipResponseDTO(c.getId(), c.getUrl(), c.getStartTime(), c.getEndTime(), c.getSubject(), c.getTags());
    }
}

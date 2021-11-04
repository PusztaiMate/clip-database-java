package hu.pumate.clipdb.controller;

import hu.pumate.clipdb.entity.Clip;
import hu.pumate.clipdb.exception.ClipNotFoundException;
import hu.pumate.clipdb.service.ClipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClipController {

    @Autowired
    private ClipService clipService;

    @GetMapping("/api/v1/clips")
    public ResponseEntity<List<Clip>> all() {
        List<Clip> clips = clipService.getAllClips();
        return new ResponseEntity<>(clips, HttpStatus.OK);
    }

    @PostMapping("/api/v1/clips")
    public ResponseEntity<Clip> newClip(@RequestBody Clip clip) {
        Clip c = clipService.addNewClip(clip);
        return new ResponseEntity<>(c, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/clips/{id}")
    public ResponseEntity<Clip> getOne(@PathVariable Long id) {
        Clip clip = clipService.getClip(id).orElseThrow(() -> new ClipNotFoundException(id));
        return new ResponseEntity<>(clip, HttpStatus.OK);
    }

    @PutMapping("/api/v1/clips/{id}")
    public ResponseEntity<Clip> updateClip(@RequestBody Clip newClip, @PathVariable Long id) {
        Clip clip = clipService.updateClip(newClip, id);
        return new ResponseEntity<>(clip, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/clips/{id}")
    public ResponseEntity<?> deleteClip(@PathVariable Long id) {
        clipService.deleteClip(id);
        return ResponseEntity.noContent().build();
    }
}

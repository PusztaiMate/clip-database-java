package hu.pumate.clipdb.service;

import hu.pumate.clipdb.entity.Clip;
import hu.pumate.clipdb.persistence.ClipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClipService {

    private final ClipRepository repository;

    public ClipService(ClipRepository repository) {
        this.repository = repository;
    }

    public Clip addNewClip(Clip clip) {
        return repository.save(clip);
    }

    public Optional<Clip> getClip(Long id) {
        return repository.findById(id);
    }

    public void deleteClip(Long id) {
        repository.deleteById(id);
    }

    public Clip updateClip(Clip newClip, Long id) {
        return repository.findById(id).map(
                        clip -> {
                            clip.setUrl(newClip.getUrl());
                            return repository.save(clip);
                        })
                .orElseGet(() -> {
                    newClip.setId(id);
                    return repository.save(newClip);
                });
    }

    public List<Clip> getAllClips() {
        return repository.findAll();
    }
}

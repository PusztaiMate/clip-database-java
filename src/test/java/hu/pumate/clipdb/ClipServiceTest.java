package hu.pumate.clipdb;

import hu.pumate.clipdb.entity.Clip;
import hu.pumate.clipdb.persistence.ClipRepository;
import hu.pumate.clipdb.service.ClipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClipServiceTest {

    private ClipRepository mockRepository;
    private ClipService clipService;

    @BeforeEach
    public void setup() {
        mockRepository = mock(ClipRepository.class);
        clipService = new ClipService(mockRepository);
    }

    @Test
    public void addNewClip() {
        Clip clip1 = createNewClip(1L, "url");
        when(mockRepository.save(clip1)).thenReturn(null);

        clipService.addNewClip(clip1);

        verify(mockRepository).save(clip1);
    }

    @Test
    public void getClip() {
        Long id = 88L;
        String url = "this://is.a.url";
        Clip c1 = createNewClip(id, url);
        when(mockRepository.findById(id)).thenReturn(Optional.of(c1));

        Optional<Clip> c2 = clipService.getClip(id);

        verify(mockRepository).findById(id);
        assertTrue(c2.isPresent());
        assertEquals(c1.getId(), c2.get().getId());
        assertEquals(c1.getUrl(), c2.get().getUrl());
    }

    @Test
    public void getClip_ClipIsNotPresent() {
        when(mockRepository.findById(any())).thenReturn(Optional.empty());

        Optional<Clip> c = clipService.getClip(1L);

        assertTrue(c.isEmpty());
    }

    @Test
    public void deleteClip() {
        Long id = 1L;
        clipService.deleteClip(id);

        verify(mockRepository).deleteById(id);
    }

    @Test
    public void updateClip_ClipIsPresent() {
        Long id = 1L;
        String urlAfter = "url2";
        Clip mockClip = mock(Clip.class);
        Clip clip2 = createNewClip(id, urlAfter);
        when(mockRepository.findById(id)).thenReturn(Optional.of(mockClip));
        when(mockRepository.save(mockClip)).thenReturn(clip2);

        Clip clip3 = clipService.updateClip(clip2, id);

        verify(mockRepository).findById(id);
        verify(mockRepository).save(mockClip);
        assertEquals(clip2.getId(), clip3.getId());
        assertEquals(clip2.getUrl(), clip3.getUrl());
    }

    @Test
    public void updateClip_ClipIsNotPresent() {
        Long id = 1L;
        Clip mc1 = mock(Clip.class);
        Clip mc2 = mock(Clip.class);
        when(mockRepository.findById(id)).thenReturn(Optional.empty());
        when(mockRepository.save(mc1)).thenReturn(mc2);

        Clip c = clipService.updateClip(mc1, 1L);

        verify(mc1).setId(1L);
        assertEquals(mc2.getId(), c.getId());
        assertEquals(mc2.getUrl(), c.getUrl());
    }

    @Test
    public void getAllClips() {
        Clip mc1 = mock(Clip.class);
        Clip mc2 = mock(Clip.class);
        when(mockRepository.findAll()).thenReturn(List.of(mc1, mc2));

        List<Clip> clips = clipService.getAllClips();

        assertEquals(2, clips.size());
        assertTrue(clips.contains(mc1));
        assertTrue(clips.contains(mc2));
    }

    private Clip createNewClip(Long id, String url) {
        Clip clip1 = new Clip();
        clip1.setUrl(url);
        clip1.setId(id);
        return clip1;
    }
}

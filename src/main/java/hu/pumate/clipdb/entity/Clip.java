package hu.pumate.clipdb.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.Objects;

@Entity
public class Clip {

    private @Id @GeneratedValue Long id;

    private String url;

    private String startTime;

    private String endTime;

    private String subject;

    @ElementCollection(targetClass = String.class)
    private List<String> tags;

    public Clip() {}

    public Clip(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clip clip = (Clip) o;
        return Objects.equals(url, clip.url) && Objects.equals(startTime, clip.startTime) && Objects.equals(endTime, clip.endTime) && Objects.equals(subject, clip.subject) && Objects.equals(tags, clip.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, startTime, endTime, subject, tags);
    }
}

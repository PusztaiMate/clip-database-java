package hu.pumate.clipdb.controller.apidescriptiors;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class GetClipResponseDTOV1 {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("subject")
    private String subject;

    public GetClipResponseDTOV1() {
    }

    public GetClipResponseDTOV1(Long id, String url, String startTime, String endTime, List<String> tags, String subject) {
        this.id = id;
        this.url = url;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = tags;
        this.subject = subject;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetClipResponseDTOV1 that = (GetClipResponseDTOV1) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UpdateClipResponseDTOV1{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", tags=" + tags +
                ", subject='" + subject + '\'' +
                '}';
    }
}

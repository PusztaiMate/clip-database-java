package hu.pumate.clipdb.controller.apidescriptiors;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class UpdateClipRequestDTOV1 {
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

    public UpdateClipRequestDTOV1() {
    }

    public UpdateClipRequestDTOV1(String url, String startTime, String endTime, List<String> tags, String subject) {
        this.url = url;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = tags;
        this.subject = subject;
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
        UpdateClipRequestDTOV1 that = (UpdateClipRequestDTOV1) o;
        return Objects.equals(url, that.url) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(tags, that.tags) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, startTime, endTime, tags, subject);
    }


    @Override
    public String toString() {
        return "UpdateClipRequestV1{" +
                "url='" + url + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", tags=" + tags +
                ", subject='" + subject + '\'' +
                '}';
    }
}

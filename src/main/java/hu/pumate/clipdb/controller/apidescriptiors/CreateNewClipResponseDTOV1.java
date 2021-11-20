package hu.pumate.clipdb.controller.apidescriptiors;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CreateNewClipResponseDTOV1 {
    @JsonProperty("id")
    private Long id;

    public CreateNewClipResponseDTOV1(Long id) {
        this.id = id;
    }

    public CreateNewClipResponseDTOV1() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateNewClipResponseDTOV1 that = (CreateNewClipResponseDTOV1) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CreateNewClipResponseDTOV1{" +
                "id=" + id +
                '}';
    }
}

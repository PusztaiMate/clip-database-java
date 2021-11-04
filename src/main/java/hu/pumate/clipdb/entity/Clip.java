package hu.pumate.clipdb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Clip {
    private @Id @GeneratedValue Long id;
    private String url;

    public Clip() {

    }

    public Clip(Long id, String url) {
        this.id = id;
        this.url = url;
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
        return Objects.equals(id, clip.id) && Objects.equals(url, clip.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }

    @Override
    public String toString() {
        return "Clip{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}

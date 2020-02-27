package com.pingsec.dev.service.dto;
import javax.persistence.Lob;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.pingsec.dev.domain.Course} entity.
 */
public class CourseDTO implements Serializable {

    private Long id;

    private String name;

    private String author;

    private String builder;

    @Lob
    private byte[] image;

    private String imageContentType;
    @Lob
    private String createrIng;

    @Lob
    private String other;

    private String score;

    private Instant latestDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getCreaterIng() {
        return createrIng;
    }

    public void setCreaterIng(String createrIng) {
        this.createrIng = createrIng;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Instant getLatestDate() {
        return latestDate;
    }

    public void setLatestDate(Instant latestDate) {
        this.latestDate = latestDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourseDTO courseDTO = (CourseDTO) o;
        if (courseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", author='" + getAuthor() + "'" +
            ", builder='" + getBuilder() + "'" +
            ", image='" + getImage() + "'" +
            ", createrIng='" + getCreaterIng() + "'" +
            ", other='" + getOther() + "'" +
            ", score='" + getScore() + "'" +
            ", latestDate='" + getLatestDate() + "'" +
            "}";
    }
}

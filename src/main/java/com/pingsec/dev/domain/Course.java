package com.pingsec.dev.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "builder")
    private String builder;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "creater_ing")
    private String createrIng;

    @Lob
    @Column(name = "other")
    private String other;

    @Column(name = "score")
    private String score;

    @Column(name = "latest_date")
    private Instant latestDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Course name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public Course author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBuilder() {
        return builder;
    }

    public Course builder(String builder) {
        this.builder = builder;
        return this;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public byte[] getImage() {
        return image;
    }

    public Course image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Course imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getCreaterIng() {
        return createrIng;
    }

    public Course createrIng(String createrIng) {
        this.createrIng = createrIng;
        return this;
    }

    public void setCreaterIng(String createrIng) {
        this.createrIng = createrIng;
    }

    public String getOther() {
        return other;
    }

    public Course other(String other) {
        this.other = other;
        return this;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getScore() {
        return score;
    }

    public Course score(String score) {
        this.score = score;
        return this;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Instant getLatestDate() {
        return latestDate;
    }

    public Course latestDate(Instant latestDate) {
        this.latestDate = latestDate;
        return this;
    }

    public void setLatestDate(Instant latestDate) {
        this.latestDate = latestDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", author='" + getAuthor() + "'" +
            ", builder='" + getBuilder() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", createrIng='" + getCreaterIng() + "'" +
            ", other='" + getOther() + "'" +
            ", score='" + getScore() + "'" +
            ", latestDate='" + getLatestDate() + "'" +
            "}";
    }
}

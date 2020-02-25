package com.pingsec.dev.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

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

    @Column(name = "creater")
    private String creater;

    @Column(name = "protect")
    private String protect;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Lob
    @Column(name = "creater_ing")
    private String createrIng;

    @Lob
    @Column(name = "other")
    private String other;

    @Column(name = "score")
    private String score;

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

    public String getCreater() {
        return creater;
    }

    public Course creater(String creater) {
        this.creater = creater;
        return this;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getProtect() {
        return protect;
    }

    public Course protect(String protect) {
        this.protect = protect;
        return this;
    }

    public void setProtect(String protect) {
        this.protect = protect;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Course picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public Course pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
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
            ", creater='" + getCreater() + "'" +
            ", protect='" + getProtect() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", createrIng='" + getCreaterIng() + "'" +
            ", other='" + getOther() + "'" +
            ", score='" + getScore() + "'" +
            "}";
    }
}

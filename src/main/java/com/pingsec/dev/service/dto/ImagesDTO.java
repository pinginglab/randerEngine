package com.pingsec.dev.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pingsec.dev.domain.Images} entity.
 */
public class ImagesDTO implements Serializable {

    private Long id;

    private String creater;

    private String name;

    private String description;

    private Instant creatTime;

    private String hashCode;

    private String port;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Instant creatTime) {
        this.creatTime = creatTime;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImagesDTO imagesDTO = (ImagesDTO) o;
        if (imagesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imagesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImagesDTO{" +
            "id=" + getId() +
            ", creater='" + getCreater() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", creatTime='" + getCreatTime() + "'" +
            ", hashCode='" + getHashCode() + "'" +
            ", port='" + getPort() + "'" +
            "}";
    }
}

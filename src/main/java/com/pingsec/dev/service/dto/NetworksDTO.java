package com.pingsec.dev.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pingsec.dev.domain.Networks} entity.
 */
public class NetworksDTO implements Serializable {

    private Long id;

    private String type;

    private String other;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NetworksDTO networksDTO = (NetworksDTO) o;
        if (networksDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), networksDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NetworksDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", other='" + getOther() + "'" +
            "}";
    }
}

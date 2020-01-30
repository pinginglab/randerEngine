package com.pingsec.dev.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.pingsec.dev.domain.App} entity.
 */
public class AppDTO implements Serializable {

    private Long id;

    private String image;

    private String network;

    private String port;

    private String volume;

    @Lob
    private String enviroment;

    @Lob
    private String other;


    private Long networksId;

    private Long imagesId;

    private Long scenesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(String enviroment) {
        this.enviroment = enviroment;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Long getNetworksId() {
        return networksId;
    }

    public void setNetworksId(Long networksId) {
        this.networksId = networksId;
    }

    public Long getImagesId() {
        return imagesId;
    }

    public void setImagesId(Long imagesId) {
        this.imagesId = imagesId;
    }

    public Long getScenesId() {
        return scenesId;
    }

    public void setScenesId(Long scenesId) {
        this.scenesId = scenesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppDTO appDTO = (AppDTO) o;
        if (appDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppDTO{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", network='" + getNetwork() + "'" +
            ", port='" + getPort() + "'" +
            ", volume='" + getVolume() + "'" +
            ", enviroment='" + getEnviroment() + "'" +
            ", other='" + getOther() + "'" +
            ", networksId=" + getNetworksId() +
            ", imagesId=" + getImagesId() +
            ", scenesId=" + getScenesId() +
            "}";
    }
}

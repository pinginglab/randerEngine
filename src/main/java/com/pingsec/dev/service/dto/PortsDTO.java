package com.pingsec.dev.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pingsec.dev.domain.Ports} entity.
 */
public class PortsDTO implements Serializable {

    private Long id;

    private String hostPort;

    private String containerPort;


    private Long appId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

    public String getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(String containerPort) {
        this.containerPort = containerPort;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PortsDTO portsDTO = (PortsDTO) o;
        if (portsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), portsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PortsDTO{" +
            "id=" + getId() +
            ", hostPort='" + getHostPort() + "'" +
            ", containerPort='" + getContainerPort() + "'" +
            ", appId=" + getAppId() +
            "}";
    }
}

package com.pingsec.dev.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Ports.
 */
@Entity
@Table(name = "ports")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ports implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "host_port")
    private String hostPort;

    @Column(name = "container_port")
    private String containerPort;

    @ManyToOne
    @JsonIgnoreProperties("ports")
    private App app;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostPort() {
        return hostPort;
    }

    public Ports hostPort(String hostPort) {
        this.hostPort = hostPort;
        return this;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

    public String getContainerPort() {
        return containerPort;
    }

    public Ports containerPort(String containerPort) {
        this.containerPort = containerPort;
        return this;
    }

    public void setContainerPort(String containerPort) {
        this.containerPort = containerPort;
    }

    public App getApp() {
        return app;
    }

    public Ports app(App app) {
        this.app = app;
        return this;
    }

    public void setApp(App app) {
        this.app = app;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ports)) {
            return false;
        }
        return id != null && id.equals(((Ports) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ports{" +
            "id=" + getId() +
            ", hostPort='" + getHostPort() + "'" +
            ", containerPort='" + getContainerPort() + "'" +
            "}";
    }
}

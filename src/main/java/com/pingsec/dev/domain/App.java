package com.pingsec.dev.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A App.
 */
@Entity
@Table(name = "app")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class App implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(name = "network")
    private String network;

    @Column(name = "port")
    private String port;

    @Column(name = "volume")
    private String volume;

    @Lob
    @Column(name = "enviroment")
    private String enviroment;

    @OneToOne
    @JoinColumn(unique = true)
    private Networks network;

    @OneToOne
    @JoinColumn(unique = true)
    private Images image;

    @OneToMany(mappedBy = "app")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ports> ports = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("apps")
    private Scenes scenes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public App image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNetwork() {
        return network;
    }

    public App network(String network) {
        this.network = network;
        return this;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getPort() {
        return port;
    }

    public App port(String port) {
        this.port = port;
        return this;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getVolume() {
        return volume;
    }

    public App volume(String volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getEnviroment() {
        return enviroment;
    }

    public App enviroment(String enviroment) {
        this.enviroment = enviroment;
        return this;
    }

    public void setEnviroment(String enviroment) {
        this.enviroment = enviroment;
    }

    public Networks getNetwork() {
        return network;
    }

    public App network(Networks networks) {
        this.network = networks;
        return this;
    }

    public void setNetwork(Networks networks) {
        this.network = networks;
    }

    public Images getImage() {
        return image;
    }

    public App image(Images images) {
        this.image = images;
        return this;
    }

    public void setImage(Images images) {
        this.image = images;
    }

    public Set<Ports> getPorts() {
        return ports;
    }

    public App ports(Set<Ports> ports) {
        this.ports = ports;
        return this;
    }

    public App addPort(Ports ports) {
        this.ports.add(ports);
        ports.setApp(this);
        return this;
    }

    public App removePort(Ports ports) {
        this.ports.remove(ports);
        ports.setApp(null);
        return this;
    }

    public void setPorts(Set<Ports> ports) {
        this.ports = ports;
    }

    public Scenes getScenes() {
        return scenes;
    }

    public App scenes(Scenes scenes) {
        this.scenes = scenes;
        return this;
    }

    public void setScenes(Scenes scenes) {
        this.scenes = scenes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof App)) {
            return false;
        }
        return id != null && id.equals(((App) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "App{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", network='" + getNetwork() + "'" +
            ", port='" + getPort() + "'" +
            ", volume='" + getVolume() + "'" +
            ", enviroment='" + getEnviroment() + "'" +
            "}";
    }
}

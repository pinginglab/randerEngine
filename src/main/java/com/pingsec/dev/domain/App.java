package com.pingsec.dev.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "environment")
    private String environment;

    @Lob
    @Column(name = "other")
    private String other;

    @OneToOne
    @JoinColumn(unique = true)
    private Networks networks;

    @OneToOne
    @JoinColumn(unique = true)
    private Images images;

    @OneToMany(mappedBy = "app")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ports> ports = new HashSet<>();

    @OneToOne(mappedBy = "app")
    @JsonIgnore
    private Tasks tasks;

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

    public String getEnvironment() {
        return environment;
    }

    public App environment(String environment) {
        this.environment = environment;
        return this;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getOther() {
        return other;
    }

    public App other(String other) {
        this.other = other;
        return this;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Networks getNetworks() {
        return networks;
    }

    public App networks(Networks networks) {
        this.networks = networks;
        return this;
    }

    public void setNetworks(Networks networks) {
        this.networks = networks;
    }

    public Images getImages() {
        return images;
    }

    public App images(Images images) {
        this.images = images;
        return this;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Set<Ports> getPorts() {
        return ports;
    }

    public App ports(Set<Ports> ports) {
        this.ports = ports;
        return this;
    }

    public App addPorts(Ports ports) {
        this.ports.add(ports);
        ports.setApp(this);
        return this;
    }

    public App removePorts(Ports ports) {
        this.ports.remove(ports);
        ports.setApp(null);
        return this;
    }

    public void setPorts(Set<Ports> ports) {
        this.ports = ports;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public App tasks(Tasks tasks) {
        this.tasks = tasks;
        return this;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
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
            ", environment='" + getEnvironment() + "'" +
            ", other='" + getOther() + "'" +
            "}";
    }
}

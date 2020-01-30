package com.pingsec.dev.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Scenes.
 */
@Entity
@Table(name = "scenes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Scenes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "creater")
    private String creater;

    @Column(name = "type")
    private String type;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "waiting_time")
    private Instant waitingTime;

    @Column(name = "extend_time")
    private Integer extendTime;

    @Column(name = "app")
    private String app;

    @Lob
    @Column(name = "other")
    private String other;

    @OneToMany(mappedBy = "scenes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<App> apps = new HashSet<>();

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

    public Scenes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreater() {
        return creater;
    }

    public Scenes creater(String creater) {
        this.creater = creater;
        return this;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getType() {
        return type;
    }

    public Scenes type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Scenes createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getWaitingTime() {
        return waitingTime;
    }

    public Scenes waitingTime(Instant waitingTime) {
        this.waitingTime = waitingTime;
        return this;
    }

    public void setWaitingTime(Instant waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Integer getExtendTime() {
        return extendTime;
    }

    public Scenes extendTime(Integer extendTime) {
        this.extendTime = extendTime;
        return this;
    }

    public void setExtendTime(Integer extendTime) {
        this.extendTime = extendTime;
    }

    public String getApp() {
        return app;
    }

    public Scenes app(String app) {
        this.app = app;
        return this;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getOther() {
        return other;
    }

    public Scenes other(String other) {
        this.other = other;
        return this;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Set<App> getApps() {
        return apps;
    }

    public Scenes apps(Set<App> apps) {
        this.apps = apps;
        return this;
    }

    public Scenes addApp(App app) {
        this.apps.add(app);
        app.setScenes(this);
        return this;
    }

    public Scenes removeApp(App app) {
        this.apps.remove(app);
        app.setScenes(null);
        return this;
    }

    public void setApps(Set<App> apps) {
        this.apps = apps;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scenes)) {
            return false;
        }
        return id != null && id.equals(((Scenes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Scenes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creater='" + getCreater() + "'" +
            ", type='" + getType() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", waitingTime='" + getWaitingTime() + "'" +
            ", extendTime=" + getExtendTime() +
            ", app='" + getApp() + "'" +
            ", other='" + getOther() + "'" +
            "}";
    }
}

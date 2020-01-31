package com.pingsec.dev.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Tasks.
 */
@Entity
@Table(name = "tasks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tasks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "build_time")
    private Instant buildTime;

    @OneToOne
    @JoinColumn(unique = true)
    private App app;

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

    public Tasks name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public Tasks content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Tasks createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getBuildTime() {
        return buildTime;
    }

    public Tasks buildTime(Instant buildTime) {
        this.buildTime = buildTime;
        return this;
    }

    public void setBuildTime(Instant buildTime) {
        this.buildTime = buildTime;
    }

    public App getApp() {
        return app;
    }

    public Tasks app(App app) {
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
        if (!(o instanceof Tasks)) {
            return false;
        }
        return id != null && id.equals(((Tasks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tasks{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", content='" + getContent() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", buildTime='" + getBuildTime() + "'" +
            "}";
    }
}

package com.pingsec.dev.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Networks.
 */
@Entity
@Table(name = "networks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Networks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "other")
    private String other;

    @OneToOne(mappedBy = "networks")
    @JsonIgnore
    private App app;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Networks type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOther() {
        return other;
    }

    public Networks other(String other) {
        this.other = other;
        return this;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public App getApp() {
        return app;
    }

    public Networks app(App app) {
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
        if (!(o instanceof Networks)) {
            return false;
        }
        return id != null && id.equals(((Networks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Networks{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", other='" + getOther() + "'" +
            "}";
    }
}

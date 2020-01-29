package com.pingsec.dev.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pingsec.dev.domain.Scenes} entity.
 */
public class ScenesDTO implements Serializable {

    private Long id;

    private String name;

    private String creater;

    private String type;

    private Instant createTime;

    private Instant waitingTime;

    private Integer extendTime;

    private String app;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Instant waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Integer getExtendTime() {
        return extendTime;
    }

    public void setExtendTime(Integer extendTime) {
        this.extendTime = extendTime;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScenesDTO scenesDTO = (ScenesDTO) o;
        if (scenesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scenesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScenesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creater='" + getCreater() + "'" +
            ", type='" + getType() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", waitingTime='" + getWaitingTime() + "'" +
            ", extendTime=" + getExtendTime() +
            ", app='" + getApp() + "'" +
            "}";
    }
}

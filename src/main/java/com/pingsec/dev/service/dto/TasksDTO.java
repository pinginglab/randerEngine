package com.pingsec.dev.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.pingsec.dev.domain.Tasks} entity.
 */
public class TasksDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private String content;

    private Instant createTime;

    private Instant buildTime;


    private Long appId;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Instant buildTime) {
        this.buildTime = buildTime;
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

        TasksDTO tasksDTO = (TasksDTO) o;
        if (tasksDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tasksDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TasksDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", content='" + getContent() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", buildTime='" + getBuildTime() + "'" +
            ", appId=" + getAppId() +
            "}";
    }
}

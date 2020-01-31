package com.pingsec.dev.service.mapper;

import com.pingsec.dev.domain.*;
import com.pingsec.dev.service.dto.TasksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tasks} and its DTO {@link TasksDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppMapper.class})
public interface TasksMapper extends EntityMapper<TasksDTO, Tasks> {

    @Mapping(source = "app.id", target = "appId")
    TasksDTO toDto(Tasks tasks);

    @Mapping(source = "appId", target = "app")
    Tasks toEntity(TasksDTO tasksDTO);

    default Tasks fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tasks tasks = new Tasks();
        tasks.setId(id);
        return tasks;
    }
}

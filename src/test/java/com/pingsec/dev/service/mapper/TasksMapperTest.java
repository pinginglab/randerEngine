package com.pingsec.dev.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TasksMapperTest {

    private TasksMapper tasksMapper;

    @BeforeEach
    public void setUp() {
        tasksMapper = new TasksMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(tasksMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tasksMapper.fromId(null)).isNull();
    }
}

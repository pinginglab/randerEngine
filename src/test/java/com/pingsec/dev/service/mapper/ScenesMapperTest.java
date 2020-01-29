package com.pingsec.dev.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ScenesMapperTest {

    private ScenesMapper scenesMapper;

    @BeforeEach
    public void setUp() {
        scenesMapper = new ScenesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(scenesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scenesMapper.fromId(null)).isNull();
    }
}

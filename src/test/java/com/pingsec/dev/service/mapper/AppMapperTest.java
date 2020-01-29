package com.pingsec.dev.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AppMapperTest {

    private AppMapper appMapper;

    @BeforeEach
    public void setUp() {
        appMapper = new AppMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(appMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(appMapper.fromId(null)).isNull();
    }
}

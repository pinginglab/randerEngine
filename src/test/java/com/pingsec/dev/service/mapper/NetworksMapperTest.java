package com.pingsec.dev.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class NetworksMapperTest {

    private NetworksMapper networksMapper;

    @BeforeEach
    public void setUp() {
        networksMapper = new NetworksMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(networksMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(networksMapper.fromId(null)).isNull();
    }
}

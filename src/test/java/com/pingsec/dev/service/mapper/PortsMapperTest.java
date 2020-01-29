package com.pingsec.dev.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PortsMapperTest {

    private PortsMapper portsMapper;

    @BeforeEach
    public void setUp() {
        portsMapper = new PortsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(portsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(portsMapper.fromId(null)).isNull();
    }
}

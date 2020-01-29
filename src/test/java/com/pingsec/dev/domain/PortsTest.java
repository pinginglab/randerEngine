package com.pingsec.dev.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pingsec.dev.web.rest.TestUtil;

public class PortsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ports.class);
        Ports ports1 = new Ports();
        ports1.setId(1L);
        Ports ports2 = new Ports();
        ports2.setId(ports1.getId());
        assertThat(ports1).isEqualTo(ports2);
        ports2.setId(2L);
        assertThat(ports1).isNotEqualTo(ports2);
        ports1.setId(null);
        assertThat(ports1).isNotEqualTo(ports2);
    }
}

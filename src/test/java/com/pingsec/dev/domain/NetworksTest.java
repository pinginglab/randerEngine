package com.pingsec.dev.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pingsec.dev.web.rest.TestUtil;

public class NetworksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Networks.class);
        Networks networks1 = new Networks();
        networks1.setId(1L);
        Networks networks2 = new Networks();
        networks2.setId(networks1.getId());
        assertThat(networks1).isEqualTo(networks2);
        networks2.setId(2L);
        assertThat(networks1).isNotEqualTo(networks2);
        networks1.setId(null);
        assertThat(networks1).isNotEqualTo(networks2);
    }
}

package com.pingsec.dev.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pingsec.dev.web.rest.TestUtil;

public class PortsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortsDTO.class);
        PortsDTO portsDTO1 = new PortsDTO();
        portsDTO1.setId(1L);
        PortsDTO portsDTO2 = new PortsDTO();
        assertThat(portsDTO1).isNotEqualTo(portsDTO2);
        portsDTO2.setId(portsDTO1.getId());
        assertThat(portsDTO1).isEqualTo(portsDTO2);
        portsDTO2.setId(2L);
        assertThat(portsDTO1).isNotEqualTo(portsDTO2);
        portsDTO1.setId(null);
        assertThat(portsDTO1).isNotEqualTo(portsDTO2);
    }
}

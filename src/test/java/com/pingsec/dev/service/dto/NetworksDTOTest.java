package com.pingsec.dev.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pingsec.dev.web.rest.TestUtil;

public class NetworksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NetworksDTO.class);
        NetworksDTO networksDTO1 = new NetworksDTO();
        networksDTO1.setId(1L);
        NetworksDTO networksDTO2 = new NetworksDTO();
        assertThat(networksDTO1).isNotEqualTo(networksDTO2);
        networksDTO2.setId(networksDTO1.getId());
        assertThat(networksDTO1).isEqualTo(networksDTO2);
        networksDTO2.setId(2L);
        assertThat(networksDTO1).isNotEqualTo(networksDTO2);
        networksDTO1.setId(null);
        assertThat(networksDTO1).isNotEqualTo(networksDTO2);
    }
}

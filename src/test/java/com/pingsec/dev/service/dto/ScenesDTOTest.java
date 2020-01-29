package com.pingsec.dev.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pingsec.dev.web.rest.TestUtil;

public class ScenesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScenesDTO.class);
        ScenesDTO scenesDTO1 = new ScenesDTO();
        scenesDTO1.setId(1L);
        ScenesDTO scenesDTO2 = new ScenesDTO();
        assertThat(scenesDTO1).isNotEqualTo(scenesDTO2);
        scenesDTO2.setId(scenesDTO1.getId());
        assertThat(scenesDTO1).isEqualTo(scenesDTO2);
        scenesDTO2.setId(2L);
        assertThat(scenesDTO1).isNotEqualTo(scenesDTO2);
        scenesDTO1.setId(null);
        assertThat(scenesDTO1).isNotEqualTo(scenesDTO2);
    }
}

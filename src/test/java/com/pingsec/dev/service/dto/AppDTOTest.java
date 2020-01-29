package com.pingsec.dev.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pingsec.dev.web.rest.TestUtil;

public class AppDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppDTO.class);
        AppDTO appDTO1 = new AppDTO();
        appDTO1.setId(1L);
        AppDTO appDTO2 = new AppDTO();
        assertThat(appDTO1).isNotEqualTo(appDTO2);
        appDTO2.setId(appDTO1.getId());
        assertThat(appDTO1).isEqualTo(appDTO2);
        appDTO2.setId(2L);
        assertThat(appDTO1).isNotEqualTo(appDTO2);
        appDTO1.setId(null);
        assertThat(appDTO1).isNotEqualTo(appDTO2);
    }
}

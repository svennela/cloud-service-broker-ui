package com.vmware.csbu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vmware.csbu.web.rest.TestUtil;

public class ServiceinstanceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceinstanceDTO.class);
        ServiceinstanceDTO serviceinstanceDTO1 = new ServiceinstanceDTO();
        serviceinstanceDTO1.setId(1L);
        ServiceinstanceDTO serviceinstanceDTO2 = new ServiceinstanceDTO();
        assertThat(serviceinstanceDTO1).isNotEqualTo(serviceinstanceDTO2);
        serviceinstanceDTO2.setId(serviceinstanceDTO1.getId());
        assertThat(serviceinstanceDTO1).isEqualTo(serviceinstanceDTO2);
        serviceinstanceDTO2.setId(2L);
        assertThat(serviceinstanceDTO1).isNotEqualTo(serviceinstanceDTO2);
        serviceinstanceDTO1.setId(null);
        assertThat(serviceinstanceDTO1).isNotEqualTo(serviceinstanceDTO2);
    }
}

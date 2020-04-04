package com.vmware.csbu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vmware.csbu.web.rest.TestUtil;

public class ServiceinstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Serviceinstance.class);
        Serviceinstance serviceinstance1 = new Serviceinstance();
        serviceinstance1.setId(1L);
        Serviceinstance serviceinstance2 = new Serviceinstance();
        serviceinstance2.setId(serviceinstance1.getId());
        assertThat(serviceinstance1).isEqualTo(serviceinstance2);
        serviceinstance2.setId(2L);
        assertThat(serviceinstance1).isNotEqualTo(serviceinstance2);
        serviceinstance1.setId(null);
        assertThat(serviceinstance1).isNotEqualTo(serviceinstance2);
    }
}

package com.vmware.csbu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vmware.csbu.web.rest.TestUtil;

public class CloudServiceBrokerUITest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CloudServiceBrokerUI.class);
        CloudServiceBrokerUI cloudServiceBrokerUI1 = new CloudServiceBrokerUI();
        cloudServiceBrokerUI1.setId(1L);
        CloudServiceBrokerUI cloudServiceBrokerUI2 = new CloudServiceBrokerUI();
        cloudServiceBrokerUI2.setId(cloudServiceBrokerUI1.getId());
        assertThat(cloudServiceBrokerUI1).isEqualTo(cloudServiceBrokerUI2);
        cloudServiceBrokerUI2.setId(2L);
        assertThat(cloudServiceBrokerUI1).isNotEqualTo(cloudServiceBrokerUI2);
        cloudServiceBrokerUI1.setId(null);
        assertThat(cloudServiceBrokerUI1).isNotEqualTo(cloudServiceBrokerUI2);
    }
}

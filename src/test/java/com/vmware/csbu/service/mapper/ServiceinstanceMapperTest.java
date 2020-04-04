package com.vmware.csbu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServiceinstanceMapperTest {

    private ServiceinstanceMapper serviceinstanceMapper;

    @BeforeEach
    public void setUp() {
        serviceinstanceMapper = new ServiceinstanceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(serviceinstanceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serviceinstanceMapper.fromId(null)).isNull();
    }
}

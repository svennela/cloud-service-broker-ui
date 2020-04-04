package com.vmware.csbu.web.rest;

import com.vmware.csbu.CloudservicebrokeruiApp;
import com.vmware.csbu.domain.Serviceinstance;
import com.vmware.csbu.repository.ServiceinstanceRepository;
import com.vmware.csbu.service.ServiceinstanceService;
import com.vmware.csbu.service.dto.ServiceinstanceDTO;
import com.vmware.csbu.service.mapper.ServiceinstanceMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ServiceinstanceResource} REST controller.
 */
@SpringBootTest(classes = CloudservicebrokeruiApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ServiceinstanceResourceIT {

    private static final String DEFAULT_INSTANCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_INSTANCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PLAN_ID = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SPACE_GUID = "AAAAAAAAAA";
    private static final String UPDATED_SPACE_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION_GUID = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_ID = "BBBBBBBBBB";

    @Autowired
    private ServiceinstanceRepository serviceinstanceRepository;

    @Autowired
    private ServiceinstanceMapper serviceinstanceMapper;

    @Autowired
    private ServiceinstanceService serviceinstanceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceinstanceMockMvc;

    private Serviceinstance serviceinstance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Serviceinstance createEntity(EntityManager em) {
        Serviceinstance serviceinstance = new Serviceinstance()
            .instanceId(DEFAULT_INSTANCE_ID)
            .name(DEFAULT_NAME)
            .serviceId(DEFAULT_SERVICE_ID)
            .planId(DEFAULT_PLAN_ID)
            .spaceGuid(DEFAULT_SPACE_GUID)
            .organizationGuid(DEFAULT_ORGANIZATION_GUID)
            .operationType(DEFAULT_OPERATION_TYPE)
            .operationId(DEFAULT_OPERATION_ID);
        return serviceinstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Serviceinstance createUpdatedEntity(EntityManager em) {
        Serviceinstance serviceinstance = new Serviceinstance()
            .instanceId(UPDATED_INSTANCE_ID)
            .name(UPDATED_NAME)
            .serviceId(UPDATED_SERVICE_ID)
            .planId(UPDATED_PLAN_ID)
            .spaceGuid(UPDATED_SPACE_GUID)
            .organizationGuid(UPDATED_ORGANIZATION_GUID)
            .operationType(UPDATED_OPERATION_TYPE)
            .operationId(UPDATED_OPERATION_ID);
        return serviceinstance;
    }

    @BeforeEach
    public void initTest() {
        serviceinstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceinstance() throws Exception {
        int databaseSizeBeforeCreate = serviceinstanceRepository.findAll().size();

        // Create the Serviceinstance
        ServiceinstanceDTO serviceinstanceDTO = serviceinstanceMapper.toDto(serviceinstance);
        restServiceinstanceMockMvc.perform(post("/api/serviceinstances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceinstanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Serviceinstance in the database
        List<Serviceinstance> serviceinstanceList = serviceinstanceRepository.findAll();
        assertThat(serviceinstanceList).hasSize(databaseSizeBeforeCreate + 1);
        Serviceinstance testServiceinstance = serviceinstanceList.get(serviceinstanceList.size() - 1);
        assertThat(testServiceinstance.getInstanceId()).isEqualTo(DEFAULT_INSTANCE_ID);
        assertThat(testServiceinstance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceinstance.getServiceId()).isEqualTo(DEFAULT_SERVICE_ID);
        assertThat(testServiceinstance.getPlanId()).isEqualTo(DEFAULT_PLAN_ID);
        assertThat(testServiceinstance.getSpaceGuid()).isEqualTo(DEFAULT_SPACE_GUID);
        assertThat(testServiceinstance.getOrganizationGuid()).isEqualTo(DEFAULT_ORGANIZATION_GUID);
        assertThat(testServiceinstance.getOperationType()).isEqualTo(DEFAULT_OPERATION_TYPE);
        assertThat(testServiceinstance.getOperationId()).isEqualTo(DEFAULT_OPERATION_ID);
    }

    @Test
    @Transactional
    public void createServiceinstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceinstanceRepository.findAll().size();

        // Create the Serviceinstance with an existing ID
        serviceinstance.setId(1L);
        ServiceinstanceDTO serviceinstanceDTO = serviceinstanceMapper.toDto(serviceinstance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceinstanceMockMvc.perform(post("/api/serviceinstances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceinstanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Serviceinstance in the database
        List<Serviceinstance> serviceinstanceList = serviceinstanceRepository.findAll();
        assertThat(serviceinstanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceinstances() throws Exception {
        // Initialize the database
        serviceinstanceRepository.saveAndFlush(serviceinstance);

        // Get all the serviceinstanceList
        restServiceinstanceMockMvc.perform(get("/api/serviceinstances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceinstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].instanceId").value(hasItem(DEFAULT_INSTANCE_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID)))
            .andExpect(jsonPath("$.[*].planId").value(hasItem(DEFAULT_PLAN_ID)))
            .andExpect(jsonPath("$.[*].spaceGuid").value(hasItem(DEFAULT_SPACE_GUID)))
            .andExpect(jsonPath("$.[*].organizationGuid").value(hasItem(DEFAULT_ORGANIZATION_GUID)))
            .andExpect(jsonPath("$.[*].operationType").value(hasItem(DEFAULT_OPERATION_TYPE)))
            .andExpect(jsonPath("$.[*].operationId").value(hasItem(DEFAULT_OPERATION_ID)));
    }
    
    @Test
    @Transactional
    public void getServiceinstance() throws Exception {
        // Initialize the database
        serviceinstanceRepository.saveAndFlush(serviceinstance);

        // Get the serviceinstance
        restServiceinstanceMockMvc.perform(get("/api/serviceinstances/{id}", serviceinstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceinstance.getId().intValue()))
            .andExpect(jsonPath("$.instanceId").value(DEFAULT_INSTANCE_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.serviceId").value(DEFAULT_SERVICE_ID))
            .andExpect(jsonPath("$.planId").value(DEFAULT_PLAN_ID))
            .andExpect(jsonPath("$.spaceGuid").value(DEFAULT_SPACE_GUID))
            .andExpect(jsonPath("$.organizationGuid").value(DEFAULT_ORGANIZATION_GUID))
            .andExpect(jsonPath("$.operationType").value(DEFAULT_OPERATION_TYPE))
            .andExpect(jsonPath("$.operationId").value(DEFAULT_OPERATION_ID));
    }

    @Test
    @Transactional
    public void getNonExistingServiceinstance() throws Exception {
        // Get the serviceinstance
        restServiceinstanceMockMvc.perform(get("/api/serviceinstances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceinstance() throws Exception {
        // Initialize the database
        serviceinstanceRepository.saveAndFlush(serviceinstance);

        int databaseSizeBeforeUpdate = serviceinstanceRepository.findAll().size();

        // Update the serviceinstance
        Serviceinstance updatedServiceinstance = serviceinstanceRepository.findById(serviceinstance.getId()).get();
        // Disconnect from session so that the updates on updatedServiceinstance are not directly saved in db
        em.detach(updatedServiceinstance);
        updatedServiceinstance
            .instanceId(UPDATED_INSTANCE_ID)
            .name(UPDATED_NAME)
            .serviceId(UPDATED_SERVICE_ID)
            .planId(UPDATED_PLAN_ID)
            .spaceGuid(UPDATED_SPACE_GUID)
            .organizationGuid(UPDATED_ORGANIZATION_GUID)
            .operationType(UPDATED_OPERATION_TYPE)
            .operationId(UPDATED_OPERATION_ID);
        ServiceinstanceDTO serviceinstanceDTO = serviceinstanceMapper.toDto(updatedServiceinstance);

        restServiceinstanceMockMvc.perform(put("/api/serviceinstances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceinstanceDTO)))
            .andExpect(status().isOk());

        // Validate the Serviceinstance in the database
        List<Serviceinstance> serviceinstanceList = serviceinstanceRepository.findAll();
        assertThat(serviceinstanceList).hasSize(databaseSizeBeforeUpdate);
        Serviceinstance testServiceinstance = serviceinstanceList.get(serviceinstanceList.size() - 1);
        assertThat(testServiceinstance.getInstanceId()).isEqualTo(UPDATED_INSTANCE_ID);
        assertThat(testServiceinstance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceinstance.getServiceId()).isEqualTo(UPDATED_SERVICE_ID);
        assertThat(testServiceinstance.getPlanId()).isEqualTo(UPDATED_PLAN_ID);
        assertThat(testServiceinstance.getSpaceGuid()).isEqualTo(UPDATED_SPACE_GUID);
        assertThat(testServiceinstance.getOrganizationGuid()).isEqualTo(UPDATED_ORGANIZATION_GUID);
        assertThat(testServiceinstance.getOperationType()).isEqualTo(UPDATED_OPERATION_TYPE);
        assertThat(testServiceinstance.getOperationId()).isEqualTo(UPDATED_OPERATION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceinstance() throws Exception {
        int databaseSizeBeforeUpdate = serviceinstanceRepository.findAll().size();

        // Create the Serviceinstance
        ServiceinstanceDTO serviceinstanceDTO = serviceinstanceMapper.toDto(serviceinstance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceinstanceMockMvc.perform(put("/api/serviceinstances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceinstanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Serviceinstance in the database
        List<Serviceinstance> serviceinstanceList = serviceinstanceRepository.findAll();
        assertThat(serviceinstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceinstance() throws Exception {
        // Initialize the database
        serviceinstanceRepository.saveAndFlush(serviceinstance);

        int databaseSizeBeforeDelete = serviceinstanceRepository.findAll().size();

        // Delete the serviceinstance
        restServiceinstanceMockMvc.perform(delete("/api/serviceinstances/{id}", serviceinstance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Serviceinstance> serviceinstanceList = serviceinstanceRepository.findAll();
        assertThat(serviceinstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

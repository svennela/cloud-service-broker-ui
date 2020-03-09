package com.vmware.csbu.web.rest;

import com.vmware.csbu.CloudservicebrokeruiApp;
import com.vmware.csbu.domain.CloudServiceBrokerUI;
import com.vmware.csbu.repository.CloudServiceBrokerUIRepository;

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
 * Integration tests for the {@link CloudServiceBrokerUIResource} REST controller.
 */
@SpringBootTest(classes = CloudservicebrokeruiApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CloudServiceBrokerUIResourceIT {

    private static final String DEFAULT_BROKER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BROKER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BROKER_URL = "AAAAAAAAAA";
    private static final String UPDATED_BROKER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_ENV = "AAAAAAAAAA";
    private static final String UPDATED_ENV = "BBBBBBBBBB";

    @Autowired
    private CloudServiceBrokerUIRepository cloudServiceBrokerUIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCloudServiceBrokerUIMockMvc;

    private CloudServiceBrokerUI cloudServiceBrokerUI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CloudServiceBrokerUI createEntity(EntityManager em) {
        CloudServiceBrokerUI cloudServiceBrokerUI = new CloudServiceBrokerUI()
            .brokerName(DEFAULT_BROKER_NAME)
            .brokerUrl(DEFAULT_BROKER_URL)
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD)
            .env(DEFAULT_ENV);
        return cloudServiceBrokerUI;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CloudServiceBrokerUI createUpdatedEntity(EntityManager em) {
        CloudServiceBrokerUI cloudServiceBrokerUI = new CloudServiceBrokerUI()
            .brokerName(UPDATED_BROKER_NAME)
            .brokerUrl(UPDATED_BROKER_URL)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .env(UPDATED_ENV);
        return cloudServiceBrokerUI;
    }

    @BeforeEach
    public void initTest() {
        cloudServiceBrokerUI = createEntity(em);
    }

    @Test
    @Transactional
    public void createCloudServiceBrokerUI() throws Exception {
        int databaseSizeBeforeCreate = cloudServiceBrokerUIRepository.findAll().size();

        // Create the CloudServiceBrokerUI
        restCloudServiceBrokerUIMockMvc.perform(post("/api/cloud-service-broker-uis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudServiceBrokerUI)))
            .andExpect(status().isCreated());

        // Validate the CloudServiceBrokerUI in the database
        List<CloudServiceBrokerUI> cloudServiceBrokerUIList = cloudServiceBrokerUIRepository.findAll();
        assertThat(cloudServiceBrokerUIList).hasSize(databaseSizeBeforeCreate + 1);
        CloudServiceBrokerUI testCloudServiceBrokerUI = cloudServiceBrokerUIList.get(cloudServiceBrokerUIList.size() - 1);
        assertThat(testCloudServiceBrokerUI.getBrokerName()).isEqualTo(DEFAULT_BROKER_NAME);
        assertThat(testCloudServiceBrokerUI.getBrokerUrl()).isEqualTo(DEFAULT_BROKER_URL);
        assertThat(testCloudServiceBrokerUI.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testCloudServiceBrokerUI.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testCloudServiceBrokerUI.getEnv()).isEqualTo(DEFAULT_ENV);
    }

    @Test
    @Transactional
    public void createCloudServiceBrokerUIWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cloudServiceBrokerUIRepository.findAll().size();

        // Create the CloudServiceBrokerUI with an existing ID
        cloudServiceBrokerUI.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCloudServiceBrokerUIMockMvc.perform(post("/api/cloud-service-broker-uis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudServiceBrokerUI)))
            .andExpect(status().isBadRequest());

        // Validate the CloudServiceBrokerUI in the database
        List<CloudServiceBrokerUI> cloudServiceBrokerUIList = cloudServiceBrokerUIRepository.findAll();
        assertThat(cloudServiceBrokerUIList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCloudServiceBrokerUIS() throws Exception {
        // Initialize the database
        cloudServiceBrokerUIRepository.saveAndFlush(cloudServiceBrokerUI);

        // Get all the cloudServiceBrokerUIList
        restCloudServiceBrokerUIMockMvc.perform(get("/api/cloud-service-broker-uis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cloudServiceBrokerUI.getId().intValue())))
            .andExpect(jsonPath("$.[*].brokerName").value(hasItem(DEFAULT_BROKER_NAME)))
            .andExpect(jsonPath("$.[*].brokerUrl").value(hasItem(DEFAULT_BROKER_URL)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].env").value(hasItem(DEFAULT_ENV)));
    }
    
    @Test
    @Transactional
    public void getCloudServiceBrokerUI() throws Exception {
        // Initialize the database
        cloudServiceBrokerUIRepository.saveAndFlush(cloudServiceBrokerUI);

        // Get the cloudServiceBrokerUI
        restCloudServiceBrokerUIMockMvc.perform(get("/api/cloud-service-broker-uis/{id}", cloudServiceBrokerUI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cloudServiceBrokerUI.getId().intValue()))
            .andExpect(jsonPath("$.brokerName").value(DEFAULT_BROKER_NAME))
            .andExpect(jsonPath("$.brokerUrl").value(DEFAULT_BROKER_URL))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.env").value(DEFAULT_ENV));
    }

    @Test
    @Transactional
    public void getNonExistingCloudServiceBrokerUI() throws Exception {
        // Get the cloudServiceBrokerUI
        restCloudServiceBrokerUIMockMvc.perform(get("/api/cloud-service-broker-uis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCloudServiceBrokerUI() throws Exception {
        // Initialize the database
        cloudServiceBrokerUIRepository.saveAndFlush(cloudServiceBrokerUI);

        int databaseSizeBeforeUpdate = cloudServiceBrokerUIRepository.findAll().size();

        // Update the cloudServiceBrokerUI
        CloudServiceBrokerUI updatedCloudServiceBrokerUI = cloudServiceBrokerUIRepository.findById(cloudServiceBrokerUI.getId()).get();
        // Disconnect from session so that the updates on updatedCloudServiceBrokerUI are not directly saved in db
        em.detach(updatedCloudServiceBrokerUI);
        updatedCloudServiceBrokerUI
            .brokerName(UPDATED_BROKER_NAME)
            .brokerUrl(UPDATED_BROKER_URL)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .env(UPDATED_ENV);

        restCloudServiceBrokerUIMockMvc.perform(put("/api/cloud-service-broker-uis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCloudServiceBrokerUI)))
            .andExpect(status().isOk());

        // Validate the CloudServiceBrokerUI in the database
        List<CloudServiceBrokerUI> cloudServiceBrokerUIList = cloudServiceBrokerUIRepository.findAll();
        assertThat(cloudServiceBrokerUIList).hasSize(databaseSizeBeforeUpdate);
        CloudServiceBrokerUI testCloudServiceBrokerUI = cloudServiceBrokerUIList.get(cloudServiceBrokerUIList.size() - 1);
        assertThat(testCloudServiceBrokerUI.getBrokerName()).isEqualTo(UPDATED_BROKER_NAME);
        assertThat(testCloudServiceBrokerUI.getBrokerUrl()).isEqualTo(UPDATED_BROKER_URL);
        assertThat(testCloudServiceBrokerUI.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testCloudServiceBrokerUI.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testCloudServiceBrokerUI.getEnv()).isEqualTo(UPDATED_ENV);
    }

    @Test
    @Transactional
    public void updateNonExistingCloudServiceBrokerUI() throws Exception {
        int databaseSizeBeforeUpdate = cloudServiceBrokerUIRepository.findAll().size();

        // Create the CloudServiceBrokerUI

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCloudServiceBrokerUIMockMvc.perform(put("/api/cloud-service-broker-uis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudServiceBrokerUI)))
            .andExpect(status().isBadRequest());

        // Validate the CloudServiceBrokerUI in the database
        List<CloudServiceBrokerUI> cloudServiceBrokerUIList = cloudServiceBrokerUIRepository.findAll();
        assertThat(cloudServiceBrokerUIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCloudServiceBrokerUI() throws Exception {
        // Initialize the database
        cloudServiceBrokerUIRepository.saveAndFlush(cloudServiceBrokerUI);

        int databaseSizeBeforeDelete = cloudServiceBrokerUIRepository.findAll().size();

        // Delete the cloudServiceBrokerUI
        restCloudServiceBrokerUIMockMvc.perform(delete("/api/cloud-service-broker-uis/{id}", cloudServiceBrokerUI.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CloudServiceBrokerUI> cloudServiceBrokerUIList = cloudServiceBrokerUIRepository.findAll();
        assertThat(cloudServiceBrokerUIList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

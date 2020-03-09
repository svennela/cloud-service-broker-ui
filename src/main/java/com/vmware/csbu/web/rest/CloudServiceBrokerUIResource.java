package com.vmware.csbu.web.rest;

import com.vmware.csbu.domain.CloudServiceBrokerUI;
import com.vmware.csbu.repository.CloudServiceBrokerUIRepository;
import com.vmware.csbu.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vmware.csbu.domain.CloudServiceBrokerUI}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CloudServiceBrokerUIResource {

    private final Logger log = LoggerFactory.getLogger(CloudServiceBrokerUIResource.class);

    private static final String ENTITY_NAME = "cloudServiceBrokerUI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CloudServiceBrokerUIRepository cloudServiceBrokerUIRepository;

    public CloudServiceBrokerUIResource(CloudServiceBrokerUIRepository cloudServiceBrokerUIRepository) {
        this.cloudServiceBrokerUIRepository = cloudServiceBrokerUIRepository;
    }

    /**
     * {@code POST  /cloud-service-broker-uis} : Create a new cloudServiceBrokerUI.
     *
     * @param cloudServiceBrokerUI the cloudServiceBrokerUI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cloudServiceBrokerUI, or with status {@code 400 (Bad Request)} if the cloudServiceBrokerUI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cloud-service-broker-uis")
    public ResponseEntity<CloudServiceBrokerUI> createCloudServiceBrokerUI(@RequestBody CloudServiceBrokerUI cloudServiceBrokerUI) throws URISyntaxException {
        log.debug("REST request to save CloudServiceBrokerUI : {}", cloudServiceBrokerUI);
        if (cloudServiceBrokerUI.getId() != null) {
            throw new BadRequestAlertException("A new cloudServiceBrokerUI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CloudServiceBrokerUI result = cloudServiceBrokerUIRepository.save(cloudServiceBrokerUI);
        return ResponseEntity.created(new URI("/api/cloud-service-broker-uis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cloud-service-broker-uis} : Updates an existing cloudServiceBrokerUI.
     *
     * @param cloudServiceBrokerUI the cloudServiceBrokerUI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cloudServiceBrokerUI,
     * or with status {@code 400 (Bad Request)} if the cloudServiceBrokerUI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cloudServiceBrokerUI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cloud-service-broker-uis")
    public ResponseEntity<CloudServiceBrokerUI> updateCloudServiceBrokerUI(@RequestBody CloudServiceBrokerUI cloudServiceBrokerUI) throws URISyntaxException {
        log.debug("REST request to update CloudServiceBrokerUI : {}", cloudServiceBrokerUI);
        if (cloudServiceBrokerUI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CloudServiceBrokerUI result = cloudServiceBrokerUIRepository.save(cloudServiceBrokerUI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cloudServiceBrokerUI.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cloud-service-broker-uis} : get all the cloudServiceBrokerUIS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cloudServiceBrokerUIS in body.
     */
    @GetMapping("/cloud-service-broker-uis")
    public List<CloudServiceBrokerUI> getAllCloudServiceBrokerUIS() {
        log.debug("REST request to get all CloudServiceBrokerUIS");
        return cloudServiceBrokerUIRepository.findAll();
    }

    /**
     * {@code GET  /cloud-service-broker-uis/:id} : get the "id" cloudServiceBrokerUI.
     *
     * @param id the id of the cloudServiceBrokerUI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cloudServiceBrokerUI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cloud-service-broker-uis/{id}")
    public ResponseEntity<CloudServiceBrokerUI> getCloudServiceBrokerUI(@PathVariable Long id) {
        log.debug("REST request to get CloudServiceBrokerUI : {}", id);
        Optional<CloudServiceBrokerUI> cloudServiceBrokerUI = cloudServiceBrokerUIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cloudServiceBrokerUI);
    }

    /**
     * {@code DELETE  /cloud-service-broker-uis/:id} : delete the "id" cloudServiceBrokerUI.
     *
     * @param id the id of the cloudServiceBrokerUI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cloud-service-broker-uis/{id}")
    public ResponseEntity<Void> deleteCloudServiceBrokerUI(@PathVariable Long id) {
        log.debug("REST request to delete CloudServiceBrokerUI : {}", id);
        cloudServiceBrokerUIRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

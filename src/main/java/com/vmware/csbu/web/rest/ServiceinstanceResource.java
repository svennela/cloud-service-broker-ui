package com.vmware.csbu.web.rest;

import com.vmware.csbu.service.ServiceinstanceService;
import com.vmware.csbu.web.rest.errors.BadRequestAlertException;
import com.vmware.csbu.service.dto.ServiceinstanceDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vmware.csbu.domain.Serviceinstance}.
 */
@RestController
@RequestMapping("/api")
public class ServiceinstanceResource {

    private final Logger log = LoggerFactory.getLogger(ServiceinstanceResource.class);

    private static final String ENTITY_NAME = "serviceinstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceinstanceService serviceinstanceService;

    public ServiceinstanceResource(ServiceinstanceService serviceinstanceService) {
        this.serviceinstanceService = serviceinstanceService;
    }

    /**
     * {@code POST  /serviceinstances} : Create a new serviceinstance.
     *
     * @param serviceinstanceDTO the serviceinstanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceinstanceDTO, or with status {@code 400 (Bad Request)} if the serviceinstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/serviceinstances")
    public ResponseEntity<ServiceinstanceDTO> createServiceinstance(@RequestBody ServiceinstanceDTO serviceinstanceDTO) throws URISyntaxException {
        log.debug("REST request to save Serviceinstance : {}", serviceinstanceDTO);
        if (serviceinstanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceinstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceinstanceDTO result = serviceinstanceService.save(serviceinstanceDTO);
        return ResponseEntity.created(new URI("/api/serviceinstances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /serviceinstances} : Updates an existing serviceinstance.
     *
     * @param serviceinstanceDTO the serviceinstanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceinstanceDTO,
     * or with status {@code 400 (Bad Request)} if the serviceinstanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceinstanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/serviceinstances")
    public ResponseEntity<ServiceinstanceDTO> updateServiceinstance(@RequestBody ServiceinstanceDTO serviceinstanceDTO) throws URISyntaxException {
        log.debug("REST request to update Serviceinstance : {}", serviceinstanceDTO);
        if (serviceinstanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceinstanceDTO result = serviceinstanceService.save(serviceinstanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceinstanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /serviceinstances} : get all the serviceinstances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceinstances in body.
     */
    @GetMapping("/serviceinstances")
    public ResponseEntity<List<ServiceinstanceDTO>> getAllServiceinstances(Pageable pageable) {
        log.debug("REST request to get a page of Serviceinstances");
        Page<ServiceinstanceDTO> page = serviceinstanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /serviceinstances/:id} : get the "id" serviceinstance.
     *
     * @param id the id of the serviceinstanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceinstanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/serviceinstances/{id}")
    public ResponseEntity<ServiceinstanceDTO> getServiceinstance(@PathVariable Long id) {
        log.debug("REST request to get Serviceinstance : {}", id);
        Optional<ServiceinstanceDTO> serviceinstanceDTO = serviceinstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceinstanceDTO);
    }

    /**
     * {@code DELETE  /serviceinstances/:id} : delete the "id" serviceinstance.
     *
     * @param id the id of the serviceinstanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/serviceinstances/{id}")
    public ResponseEntity<Void> deleteServiceinstance(@PathVariable Long id) {
        log.debug("REST request to delete Serviceinstance : {}", id);
        serviceinstanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

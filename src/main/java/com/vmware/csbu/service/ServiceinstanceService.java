package com.vmware.csbu.service;

import com.vmware.csbu.domain.Serviceinstance;
import com.vmware.csbu.repository.ServiceinstanceRepository;
import com.vmware.csbu.service.dto.ServiceinstanceDTO;
import com.vmware.csbu.service.mapper.ServiceinstanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Serviceinstance}.
 */
@Service
@Transactional
public class ServiceinstanceService {

    private final Logger log = LoggerFactory.getLogger(ServiceinstanceService.class);

    private final ServiceinstanceRepository serviceinstanceRepository;

    private final ServiceinstanceMapper serviceinstanceMapper;

    public ServiceinstanceService(ServiceinstanceRepository serviceinstanceRepository, ServiceinstanceMapper serviceinstanceMapper) {
        this.serviceinstanceRepository = serviceinstanceRepository;
        this.serviceinstanceMapper = serviceinstanceMapper;
    }

    /**
     * Save a serviceinstance.
     *
     * @param serviceinstanceDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceinstanceDTO save(ServiceinstanceDTO serviceinstanceDTO) {
        log.debug("Request to save Serviceinstance : {}", serviceinstanceDTO);
        Serviceinstance serviceinstance = serviceinstanceMapper.toEntity(serviceinstanceDTO);
        serviceinstance = serviceinstanceRepository.save(serviceinstance);
        return serviceinstanceMapper.toDto(serviceinstance);
    }

    /**
     * Get all the serviceinstances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceinstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Serviceinstances");
        return serviceinstanceRepository.findAll(pageable)
            .map(serviceinstanceMapper::toDto);
    }

    /**
     * Get one serviceinstance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceinstanceDTO> findOne(Long id) {
        log.debug("Request to get Serviceinstance : {}", id);
        return serviceinstanceRepository.findById(id)
            .map(serviceinstanceMapper::toDto);
    }

    /**
     * Delete the serviceinstance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Serviceinstance : {}", id);
        serviceinstanceRepository.deleteById(id);
    }
}

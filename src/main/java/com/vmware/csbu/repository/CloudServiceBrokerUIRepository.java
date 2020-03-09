package com.vmware.csbu.repository;

import com.vmware.csbu.domain.CloudServiceBrokerUI;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CloudServiceBrokerUI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CloudServiceBrokerUIRepository extends JpaRepository<CloudServiceBrokerUI, Long> {
}

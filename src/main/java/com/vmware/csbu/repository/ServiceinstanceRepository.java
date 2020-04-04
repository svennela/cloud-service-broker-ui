package com.vmware.csbu.repository;

import com.vmware.csbu.domain.Serviceinstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Serviceinstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceinstanceRepository extends JpaRepository<Serviceinstance, Long> {
}

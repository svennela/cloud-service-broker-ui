package com.vmware.csbu.service.mapper;


import com.vmware.csbu.domain.*;
import com.vmware.csbu.service.dto.ServiceinstanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Serviceinstance} and its DTO {@link ServiceinstanceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceinstanceMapper extends EntityMapper<ServiceinstanceDTO, Serviceinstance> {



    default Serviceinstance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Serviceinstance serviceinstance = new Serviceinstance();
        serviceinstance.setId(id);
        return serviceinstance;
    }
}

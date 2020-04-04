package com.vmware.csbu.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vmware.csbu.domain.Serviceinstance} entity.
 */
public class ServiceinstanceDTO implements Serializable {
    
    private Long id;

    private Long brokerId;

    private String brokerName;

    private String planName;

    private String serviceName;

    private String instanceId;

    private String name;

    private String serviceId;

    private String planId;

    private String spaceGuid;

    private String organizationGuid;

    private String operationType;

    private String operationId;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getSpaceGuid() {
        return spaceGuid;
    }

    public void setSpaceGuid(String spaceGuid) {
        this.spaceGuid = spaceGuid;
    }

    public String getOrganizationGuid() {
        return organizationGuid;
    }

    public void setOrganizationGuid(String organizationGuid) {
        this.organizationGuid = organizationGuid;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceinstanceDTO serviceinstanceDTO = (ServiceinstanceDTO) o;
        if (serviceinstanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceinstanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceinstanceDTO{" +
            "id=" + getId() +
            ", instanceId='" + getInstanceId() + "'" +
            ", name='" + getName() + "'" +
            ", serviceId='" + getServiceId() + "'" +
            ", planId='" + getPlanId() + "'" +
            ", spaceGuid='" + getSpaceGuid() + "'" +
            ", organizationGuid='" + getOrganizationGuid() + "'" +
            ", operationType='" + getOperationType() + "'" +
            ", operationId='" + getOperationId() + "'" +
            "}";
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }



}

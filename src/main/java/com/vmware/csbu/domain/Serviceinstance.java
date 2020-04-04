package com.vmware.csbu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serviceinstance.
 */
@Entity
@Table(name = "serviceinstance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Serviceinstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "broker_id")
    private Long brokerId;

    @Column(name = "instance_id")
    private String instanceId;

    @Column(name = "name")
    private String name;

    @Column(name = "broker_name")
    private String brokerName;

    @Column(name = "plan_name")
    private String planName;
    
    @Column(name = "service_name")
    private String serviceName;


    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "plan_id")
    private String planId;

    @Column(name = "space_guid")
    private String spaceGuid;

    @Column(name = "organization_guid")
    private String organizationGuid;

    @Column(name = "operation_type")
    private String operationType;

    @Column(name = "operation_id")
    private String operationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public Serviceinstance instanceId(String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getName() {
        return name;
    }

    public Serviceinstance name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceId() {
        return serviceId;
    }

    public Serviceinstance serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPlanId() {
        return planId;
    }

    public Serviceinstance planId(String planId) {
        this.planId = planId;
        return this;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getSpaceGuid() {
        return spaceGuid;
    }

    public Serviceinstance spaceGuid(String spaceGuid) {
        this.spaceGuid = spaceGuid;
        return this;
    }

    public void setSpaceGuid(String spaceGuid) {
        this.spaceGuid = spaceGuid;
    }

    public String getOrganizationGuid() {
        return organizationGuid;
    }

    public Serviceinstance organizationGuid(String organizationGuid) {
        this.organizationGuid = organizationGuid;
        return this;
    }

    public void setOrganizationGuid(String organizationGuid) {
        this.organizationGuid = organizationGuid;
    }

    public String getOperationType() {
        return operationType;
    }

    public Serviceinstance operationType(String operationType) {
        this.operationType = operationType;
        return this;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationId() {
        return operationId;
    }

    public Serviceinstance operationId(String operationId) {
        this.operationId = operationId;
        return this;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Serviceinstance)) {
            return false;
        }
        return id != null && id.equals(((Serviceinstance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Serviceinstance{" +
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

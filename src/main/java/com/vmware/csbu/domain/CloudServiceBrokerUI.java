package com.vmware.csbu.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * The CloudServiceBrokerUI entity.
 */
@ApiModel(description = "The CloudServiceBrokerUI entity.")
@Entity
@Table(name = "cloud_service_broker_ui")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CloudServiceBrokerUI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "broker_name")
    private String brokerName;

    @Column(name = "broker_url")
    private String brokerUrl;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "env")
    private String env;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public CloudServiceBrokerUI brokerName(String brokerName) {
        this.brokerName = brokerName;
        return this;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public CloudServiceBrokerUI brokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
        return this;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getUserName() {
        return userName;
    }

    public CloudServiceBrokerUI userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public CloudServiceBrokerUI password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnv() {
        return env;
    }

    public CloudServiceBrokerUI env(String env) {
        this.env = env;
        return this;
    }

    public void setEnv(String env) {
        this.env = env;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CloudServiceBrokerUI)) {
            return false;
        }
        return id != null && id.equals(((CloudServiceBrokerUI) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CloudServiceBrokerUI{" +
            "id=" + getId() +
            ", brokerName='" + getBrokerName() + "'" +
            ", brokerUrl='" + getBrokerUrl() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", env='" + getEnv() + "'" +
            "}";
    }
}

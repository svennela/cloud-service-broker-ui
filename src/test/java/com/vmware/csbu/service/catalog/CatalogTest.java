package com.vmware.csbu.service.catalog;

import com.vmware.csbu.domain.CloudServiceBrokerUI;
import com.vmware.csbu.domain.User;
import com.vmware.csbu.model.catalog.Catalog;
import com.vmware.csbu.service.CloudServiceBrokerService;
import com.vmware.csbu.service.dto.ServiceinstanceDTO;
import com.vmware.csbu.service.dto.UserDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vmware.csbu.CloudservicebrokeruiApp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken; 

@SpringBootTest(classes = CloudservicebrokeruiApp.class)
public class CatalogTest {

    @Autowired
    CloudServiceBrokerService cloudServiceBrokerService;

    CloudServiceBrokerUI serviceBroker;
    ServiceinstanceDTO serviceinstance;

    @BeforeEach
    public void init() {
      serviceBroker = new CloudServiceBrokerUI();
      serviceBroker.setBrokerUrl("http://localhost:8080");
      serviceBroker.setUserName("brokerapp");
      serviceBroker.setPassword("password");
      serviceinstance = new ServiceinstanceDTO();
      serviceinstance.setInstanceId("Lsw2DVGwkj");
      serviceinstance.setPlanId("4ba45322-534c-11ea-b346-00155da9789e");
      serviceinstance.setServiceId("e5d2898e-534a-11ea-a4e8-00155da9789e");
      // serviceinstance.setOrganizationGuid("organizationGuid");
      // serviceinstance.setSpaceGuid("spaceGuid");
    }
     @Test
     public void createserviceinstance() {


      ServiceinstanceDTO obj=cloudServiceBrokerService.provisionService(serviceBroker, serviceinstance);

      //assertThat(obj).isNotNull();
      
    }


    @Test
    public void deleteserviceinstance() {


     ServiceinstanceDTO obj=cloudServiceBrokerService.deprovisionService( serviceinstance,serviceBroker);

     //assertThat(obj).isNotNull();
     
   }
}   
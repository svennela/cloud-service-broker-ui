package com.vmware.csbu.service;

import java.nio.charset.StandardCharsets;
import com.vmware.csbu.domain.CloudServiceBrokerUI;
import com.vmware.csbu.domain.Serviceinstance;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import com.vmware.csbu.model.catalog.Catalog;
import com.vmware.csbu.repository.ServiceinstanceRepository;
import com.vmware.csbu.service.dto.ServiceinstanceDTO;
import com.vmware.csbu.service.mapper.ServiceinstanceMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CloudServiceBrokerService {

    ObjectMapper mapper = new ObjectMapper();

    CloseableHttpClient httpClient;

    private final ServiceinstanceRepository serviceinstanceRepository;

    private final ServiceinstanceMapper serviceinstanceMapper;
    

    public CloudServiceBrokerService(ServiceinstanceRepository serviceinstanceRepository, ServiceinstanceMapper serviceinstanceMapper){
        this.serviceinstanceRepository = serviceinstanceRepository;
        this.serviceinstanceMapper = serviceinstanceMapper;
    }

    public Catalog getBrokerCatalog(CloudServiceBrokerUI serviceBroker){
        try {
           
  
            // new
  
            final SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .build();
                final NoopHostnameVerifier hostnameVerifier = new NoopHostnameVerifier();

                final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
                 httpClient = HttpClients.custom()
                    .setSSLHostnameVerifier(hostnameVerifier)
                    //.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .setSSLSocketFactory(sslsf)
                    .build();
            HttpGet gettMethod = new HttpGet(serviceBroker.getBrokerUrl()+"/v2/catalog");
  
            String auth = serviceBroker.getUserName() + ":" + serviceBroker.getPassword();
            byte[] encodedAuth = org.apache.commons.codec.binary.Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);
  
            gettMethod.addHeader("authorization", authHeaderValue  );
            gettMethod.addHeader("accept", "application/json");
            gettMethod.addHeader("X-Broker-API-Version", "2.14");
  
            final HttpResponse response = httpClient.execute(gettMethod);
            HttpEntity entity = response.getEntity();
  
            // Read the contents of an entity and return it as a String.
            String json_data  = EntityUtils.toString(entity);
  
            System.out.println(json_data);
            //JSONObject jsonObj = new JSONObject(content);

             // 2. convert JSON array to List of objects
             //List<Catalog> catalogs = Arrays.asList(mapper.readValue(json_data, Catalog[].class));
             Catalog Catalogobj = mapper.readValue(json_data, Catalog.class);   
             
             System.out.println(Catalogobj);   

             return Catalogobj;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }


    // # Provision MySQL
    // curl -H 'Accept: application/json' -H 'Content-Type: application/json' 
    // -X PUT "http://username:password@localhost:3000/v2/service_instances/testmy?accepts_incomplete=true" 
    // -d '{"service_id":"ce71b484-d542-40f7-9dd4-5526e38c81ba","plan_id":"5b8282cf-a669-4ffc-b426-c169a7bbfc71",
    // "organization_guid":"organization_id","space_guid":"space_id"}'
    
    // # Last Operation MySQL
    // curl -H 'Accept: application/json' -H 'Content-Type: application/json' -X GET
    // "http://username:password@localhost:3000/v2/service_instances/testmy/last_operation"

    
    public ServiceinstanceDTO provisionService(CloudServiceBrokerUI serviceBroker,ServiceinstanceDTO serviceinstance){
        try {
           
            final SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .build();
                final NoopHostnameVerifier hostnameVerifier = new NoopHostnameVerifier();

                final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
                 httpClient = HttpClients.custom()
                    .setSSLHostnameVerifier(hostnameVerifier)
                    //.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .setSSLSocketFactory(sslsf)
                    .build();
            String instanceIdString = RandomStringUtils.randomAlphanumeric(10);
            System.out.println(instanceIdString);
            String url = serviceBroker.getBrokerUrl()+"/v2/service_instances/"+instanceIdString+"?accepts_incomplete=true";
            System.out.println(url);
            HttpPut httpPost = new HttpPut(url);
            serviceinstance.setInstanceId(instanceIdString);
            String auth = serviceBroker.getUserName() + ":" + serviceBroker.getPassword();
            byte[] encodedAuth = org.apache.commons.codec.binary.Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);
  
            httpPost.addHeader("authorization", authHeaderValue  );
            httpPost.addHeader("accept", "application/json");
            httpPost.addHeader("X-Broker-API-Version", "2.14");
            
            // StringBuilder servicejson = new StringBuilder();
            // servicejson.append("{");
            // servicejson.append("\"service_id\":\"+serviceinstance.getServiceId()+\"");
            // servicejson.append("\"plan_id\":\"+serviceinstance.getPlanId()+\"");
            // servicejson.append("\"organization_guid\":\"+serviceinstance.getOrganizationGuid()+\"");
            // servicejson.append("\"space_guid\":\"+serviceinstance.space_guid()+\"");
            // servicejson.append("}");

           // StringEntity st = new StringEntity("{\"service_id\":\"" + serviceinstance.getServiceId() + "\",\"plan_id\":\"" + serviceinstance.getPlanId() + "\",\"organization_guid\":\"" + serviceinstance.getOrganizationGuid()+ "\",\"space_guid\":\"" + serviceinstance.getSpaceGuid()+"\"}" );
           StringEntity st = new StringEntity("{\"service_id\":\"" + serviceinstance.getServiceId() + "\",\"plan_id\":\"" + serviceinstance.getPlanId() +"\"}" );
           
           // StringEntity input = new StringEntity("{\"service_id\":\"+serviceinstance.getServiceId()+\"}");

            // String payload = "data={" +
            // "\"username\": \"admin\", " +
            // "\"first_name\": \"System\", " +
            // "\"last_name\": \"Administrator\"" +
            // "}";
            
            System.out.println(st);
            // send a JSON data
            httpPost.setEntity(st);

            final HttpResponse response = httpClient.execute(httpPost);
            
            HttpEntity entity = response.getEntity();
            System.out.println(entity);
            // Read the contents of an entity and return it as a String.
            String json_data  = EntityUtils.toString(entity);
  
            System.out.println(json_data);
            
            if(json_data.indexOf("service-id not in the catalog") > 0 ){
                return null;
            }else{
                // save the response in the database.
                serviceinstance.setOperationId("in progress");
                serviceinstance.setName(serviceBroker.getBrokerName());
                serviceinstance.setBrokerId(serviceBroker.getId());
                // Valid values are in progress, succeeded, and failed
                Serviceinstance serviceinstanceObj = serviceinstanceMapper.toEntity(serviceinstance);
                serviceinstanceObj = serviceinstanceRepository.save(serviceinstanceObj);
                return serviceinstance;
            }

             
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        
    }

    // curl 'http://username:password@service-broker-url/v2/service_instances/:instance_id?accepts_incomplete=true
    //  &service_id=service-offering-id-here&plan_id=service-plan-id-here' -X DELETE -H "X-Broker-API-Version: 2.14"
    
    public ServiceinstanceDTO deprovisionService(ServiceinstanceDTO serviceinstance, CloudServiceBrokerUI cloudServiceBrokerUI){

       try {
           
            final SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .build();
                final NoopHostnameVerifier hostnameVerifier = new NoopHostnameVerifier();

                final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
                 httpClient = HttpClients.custom()
                    .setSSLHostnameVerifier(hostnameVerifier)
                    //.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .setSSLSocketFactory(sslsf)
                    .build();
           
            String url = cloudServiceBrokerUI.getBrokerUrl()+"/v2/service_instances/"+serviceinstance.getInstanceId()+"?accepts_incomplete=true&service_id="+serviceinstance.getServiceId()+"&plan_id="+serviceinstance.getPlanId();
            System.out.println(url);

            HttpDelete httpDelete = new HttpDelete(url);
            String auth = cloudServiceBrokerUI.getUserName() + ":" + cloudServiceBrokerUI.getPassword();
            byte[] encodedAuth = org.apache.commons.codec.binary.Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);
  
            httpDelete.addHeader("authorization", authHeaderValue  );
            httpDelete.addHeader("accept", "application/json");
            httpDelete.addHeader("X-Broker-API-Version", "2.14");
            
            
            final HttpResponse response = httpClient.execute(httpDelete);
            
            HttpEntity entity = response.getEntity();
            System.out.println(entity);
            // Read the contents of an entity and return it as a String.
            String json_data  = EntityUtils.toString(entity);
            
            if(json_data.indexOf("service-id not in the catalog") > 0 ){
                return null;
            }else{
                // // save the response in the database.
                serviceinstance.setOperationId("Deleted");
                // Valid values are in progress, succeeded, and failed
                Serviceinstance serviceinstanceObj = serviceinstanceMapper.toEntity(serviceinstance);
                serviceinstanceObj = serviceinstanceRepository.save(serviceinstanceObj);
                return serviceinstance;
          
            }

             
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    } 
}


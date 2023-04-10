package com.beaconfire.applicationcompositeservice.service;

import com.beaconfire.applicationcompositeservice.domain.EmployeeService.request.EmployeeApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CompositeService {

    private RestTemplate restTemplate;


    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Using RestTemplate
    public ResponseEntity<String> uploadEmployeeInfo(EmployeeApplicationRequest empRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://employee-service/employee-service/employee-service", empRequest, String.class
        );
        return response;
    }

    public ResponseEntity<String> uploadFile(String folder, MultipartFile file) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", file.getResource());
        map.add("folder", folder);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://storage-service/storage-service/storage-service/upload", HttpMethod.POST, requestEntity, String.class
        );

        return response;
    }


}

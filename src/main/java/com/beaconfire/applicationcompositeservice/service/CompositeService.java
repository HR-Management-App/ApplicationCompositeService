package com.beaconfire.applicationcompositeservice.service;

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

//    private RemoteHousingService housingService;

    private RestTemplate restTemplate;

//    @Autowired
//    public void setHousingService(RemoteHousingService housingService) {
//        this.housingService = housingService;
//    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Using RestTemplate
//    public AllLandlordResponse getLandlordRest() {
//        ResponseEntity<List<Landlord>> landlordResponse = restTemplate.exchange(
//                "http://housing-service/housing-service/housing-service", HttpMethod.GET, null, new ParameterizedTypeReference<List<Landlord>>() {
//                }
//        );
//
//        return AllLandlordResponse.builder()
//                .landlordList(landlordResponse.getBody())
//                .build();
//    }

    public ResponseEntity<String> uploadFile(MultipartFile file) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://storage-service/storage-service/storage-service/upload", HttpMethod.POST, requestEntity, String.class
        );

        return response;
    }


}

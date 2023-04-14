package com.beaconfire.applicationcompositeservice.service;

import com.beaconfire.applicationcompositeservice.domain.ApplicationService.ApplicationWorkFlow;
import com.beaconfire.applicationcompositeservice.domain.ApplicationService.request.ApplicationUpdateRequest;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.request.EmployeeApplicationRequest;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.request.FilePathRequest;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.response.EmployeeProfileResponse;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.response.FinalProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    public Integer findEmployeeIdByUserId(int user_id) {
        ResponseEntity<Integer> response = restTemplate.exchange(
                "http://employee-service/employee-service/employee-service/" + user_id, HttpMethod.GET, null, Integer.class
        );
        Integer employee_id = response.getBody();
        return employee_id;
    }

    public ApplicationWorkFlow getApplicationStatusByEmployeeID(int employee_id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//        map.add("employee_id", employee_id);
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, httpHeaders);
        HttpEntity requestEntity = new HttpEntity(httpHeaders);

        ResponseEntity<ApplicationWorkFlow> response = restTemplate.exchange(
                "http://application-service/application-service/application-service/status?employee_id={employee_id}",
                HttpMethod.GET, requestEntity , ApplicationWorkFlow.class, employee_id
        );
        return response.getBody();
    }

    public Integer createNewApplication(int employee_id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<Integer> response = restTemplate.exchange(
                "http://application-service/application-service/application-service/create?employee_id={employee_id}",
                HttpMethod.GET, requestEntity , Integer.class, employee_id
        );
        return response.getBody();
    }

    public void updateApplication(ApplicationUpdateRequest request) {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity requestEntity = new HttpEntity(httpHeaders);
//        ResponseEntity<ApplicationWorkFlow> response = restTemplate.exchange(
//                "http://application-service/application-service/application-service/update/{app_id}/{status}",
//                HttpMethod.PUT, requestEntity , ApplicationWorkFlow.class, app_id, status
//        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ApplicationUpdateRequest> requestEntity = new HttpEntity(request, httpHeaders);
        ResponseEntity<ApplicationWorkFlow> response = restTemplate.exchange(
                "http://application-service/application-service/application-service/update",
                HttpMethod.PUT, requestEntity , ApplicationWorkFlow.class
        );

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        restTemplate.put(
//                "http://application-service/application-service/application-service/update",
//                request , ApplicationWorkFlow.class);


    }

    public ResponseEntity<String> getDocumentPath(int emp_id, String title) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://employee-service/employee-service/employee-service/document?emp_id={emp_id}&title={title}",
                HttpMethod.GET, requestEntity , String.class, emp_id, title
        );
        return response;
    }

    public ResponseEntity<ByteArrayResource> downloadFile(String filename) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<ByteArrayResource> response = restTemplate.exchange(
                "http://storage-service/storage-service/storage-service/download?filename={filename}",
                HttpMethod.GET, requestEntity , ByteArrayResource.class,filename
        );
        return response;
    }

    @GetMapping("/digital")
    public ResponseEntity<String> getDigitalDocument(String type) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://application-service/application-service/application-service/digital?type={type}",
                HttpMethod.GET, requestEntity , String.class, type
        );
        return response;
    }

    public FinalProfileResponse getEmployeeProfile(int employee_id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<EmployeeProfileResponse> response = restTemplate.exchange(
                "http://employee-service/employee-service/employee-service/{id}/profile",
                HttpMethod.GET, requestEntity , EmployeeProfileResponse.class, employee_id
        );

        EmployeeProfileResponse emp = response.getBody();
        FinalProfileResponse profileResponse = FinalProfileResponse.builder()
                .firstName(emp.getFirstName())
                .lastName(emp.getLastName())
                .middleName(emp.getMiddleName())
                .preferredName(emp.getPreferredName())
                .profilePictureUrl(emp.getProfilePictureUrl())
                .email(emp.getEmail())
                .dob(emp.getDob())
                .gender(emp.getGender())
                .currentAddress(emp.getCurrentAddress())
                .cellPhoneNumber(emp.getCellPhoneNumber())
                .visaStatus(emp.getVisaStatus().getVisaType())
                .driverLicense(emp.getDriverLicense())
                .build();
        return profileResponse;
    }

    public void updatePathFileByTitle(FilePathRequest filePathRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.put(
                "http://employee-service/employee-service/employee-service/updatePath",
                filePathRequest , Void.class);
    }

    public boolean getVisaFlag(int emp_id, String type) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<Boolean> response = restTemplate.exchange(
                "http://employee-service/employee-service/employee-service/getActiveFlag?emp_id=" + emp_id + "&type=" +type,
                HttpMethod.GET, requestEntity , Boolean.class
        );
        return response.getBody();
    }

}

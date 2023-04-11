package com.beaconfire.applicationcompositeservice.controller;

import com.beaconfire.applicationcompositeservice.domain.ApplicationService.ApplicationWorkFlow;
import com.beaconfire.applicationcompositeservice.domain.ApplicationService.misc.ApplicationStatus;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.request.EmployeeApplicationRequest;
import com.beaconfire.applicationcompositeservice.security.TokenUserDetail;
import com.beaconfire.applicationcompositeservice.service.CompositeService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.json.JSONObject;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("composite")
public class CompositeController {

    private CompositeService service;

    @Autowired
    public void setCompositeService(CompositeService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('employee')")
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file,
                                             @RequestPart(value = "folder") String folder){
        return service.uploadFile(folder, file);
    }

    @PostMapping(value = "/uploadEmpInfo")
    public ResponseEntity<String> uploadEmpInfo(@RequestBody EmployeeApplicationRequest empRequest) {
        ResponseEntity<String> response = service.uploadEmployeeInfo(empRequest);
        JSONObject json = new JSONObject(response.getBody());
        int emp_id = json.getInt("emp_id");
        int app_id = service.createNewApplication(emp_id);
        return new ResponseEntity<>("{\"app_id\" : \""+ app_id + "\"," + "\"emp_id\" : \""+ emp_id + "\"}", HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<String> getApplicationStatus() {
        int userID = ((TokenUserDetail) SecurityContextHolder.getContext().getAuthentication().getDetails()).getUser_id();
        int employeeID = service.findEmployeeIdByUserId(userID);
        if (employeeID != -1) { // not submitted application yet
            ApplicationWorkFlow app = service.getApplicationStatusByEmployeeID(employeeID);
            return new ResponseEntity<>("{\"status\" : \""+ app.getStatus() + "\"}", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"status\" : \""+ ApplicationStatus.NEVER_SUBMITTED + "\"}", HttpStatus.OK);
        }

    }


//    @GetMapping("/app")
//    public ApplicationResponse getAppInfo() {return compositeService.getApplicationInfo();}
}

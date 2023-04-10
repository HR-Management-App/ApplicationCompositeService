package com.beaconfire.applicationcompositeservice.controller;


import com.beaconfire.applicationcompositeservice.domain.EmployeeService.request.EmployeeApplicationRequest;
import com.beaconfire.applicationcompositeservice.service.CompositeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("composite")
public class CompositeController {

    private CompositeService service;

    @Autowired
    public void setCompositeService(CompositeService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file,
                                             @RequestPart(value = "folder") String folder){

        return service.uploadFile(folder, file);
    }

    @PostMapping(value = "/uploadEmpInfo")
    public ResponseEntity<String> uploadFile(@RequestBody EmployeeApplicationRequest empRequest) {

        return service.uploadEmployeeInfo(empRequest);
    }


//    @GetMapping("/app")
//    public ApplicationResponse getAppInfo() {return compositeService.getApplicationInfo();}
}

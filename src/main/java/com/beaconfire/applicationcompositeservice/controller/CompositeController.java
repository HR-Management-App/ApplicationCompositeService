package com.beaconfire.applicationcompositeservice.controller;

import com.beaconfire.applicationcompositeservice.service.CompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file){

        return service.uploadFile(file);
    }


//    @GetMapping("/app")
//    public ApplicationResponse getAppInfo() {return compositeService.getApplicationInfo();}
}

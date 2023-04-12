package com.beaconfire.applicationcompositeservice.controller;

import com.beaconfire.applicationcompositeservice.domain.ApplicationService.ApplicationWorkFlow;
import com.beaconfire.applicationcompositeservice.domain.ApplicationService.misc.ApplicationStatus;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.request.EmployeeApplicationRequest;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.response.FinalProfileResponse;
import com.beaconfire.applicationcompositeservice.security.TokenUserDetail;
import com.beaconfire.applicationcompositeservice.service.CompositeService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
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

    // HR
    @PutMapping ("/update/{app_id}/{status}")
    public void updateApplication(@PathVariable int app_id, @PathVariable String status) {
        service.updateApplication(app_id, status);
    }

    @GetMapping ("/{user_id}")
    public ResponseEntity<String> findEmployeeIdByUserId (@PathVariable int user_id) {
        int emp_id = service.findEmployeeIdByUserId(user_id);
        return new ResponseEntity<>("{\"emp_id\" : \"" + emp_id + "\"}", HttpStatus.OK);
    }

    @GetMapping ("/document")
    public ResponseEntity<String> getDocumentPath (@RequestParam int emp_id,
                                                   @RequestParam String title) {
        return service.getDocumentPath(emp_id, title);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String filename) {
        return service.downloadFile(filename);
    }

    @GetMapping("/digital")
    public ResponseEntity<String> getDigitalDocument(@RequestParam String type) {
        return service.getDigitalDocument(type);
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<FinalProfileResponse> getEmployeeProfile(@PathVariable int id) {
        return new ResponseEntity<>(service.getEmployeeProfile(id), HttpStatus.OK);
    }

}

package com.demo.ams.controller;

import com.demo.ams.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertController {

    @Autowired
    private AlertService alertService;

    /**
     * Request authentication & authorization(@PreAuthorize) can be added by configuring spring security
     * */
    @PostMapping("/{teamId}/alert")
    public ResponseEntity<?> alert(@PathVariable(value = "teamId") Long teamId) {
        alertService.processAlertRequest(teamId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

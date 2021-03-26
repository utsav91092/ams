package com.demo.ams.controller;

import com.demo.ams.dto.CreateTeamRequest;
import com.demo.ams.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    /**
    * Request authentication & authorization(@PreAuthorize) can be added by configuring spring security
    * */
    @PostMapping("/team")
    public ResponseEntity<?> createTeam(@RequestBody CreateTeamRequest createTeamRequest) {
        return new ResponseEntity<>(teamService.createTeam(createTeamRequest), HttpStatus.OK);
    }
}

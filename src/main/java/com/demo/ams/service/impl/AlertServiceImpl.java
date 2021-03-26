package com.demo.ams.service.impl;

import com.demo.ams.AlertType;
import com.demo.ams.entity.Developer;
import com.demo.ams.entity.Team;
import com.demo.ams.exception.ApiException;
import com.demo.ams.service.AlertFactory;
import com.demo.ams.service.AlertService;
import com.demo.ams.service.TeamService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AlertServiceImpl implements AlertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertServiceImpl.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private AlertFactory alertFactory;

    @Override
    public void processAlertRequest(Long teamId) throws ApiException {
        Optional<Team> team = teamService.getTeamById(teamId);
        team.orElseThrow(()-> new ApiException("Team not found", HttpStatus.NOT_FOUND));
        List<Developer> developers = team.get().getDevelopers();
        if(CollectionUtils.isEmpty(developers))
            throw new ApiException("No developers belongs to the team, hence alert cant be sent", HttpStatus.PRECONDITION_FAILED);
        Developer developer = chooseAnyDeveloper(team.get().getDevelopers());
        sendAlert(AlertType.SMS, developer);
    }

    private void sendAlert(AlertType alertType, Developer developer) {
        LOGGER.info("Sending alert to {}",developer.getFullName());
        ensurePhoneNumberExists(developer.getPhoneNumber());
        alertFactory.getAlertService(alertType).alert(developer);
        LOGGER.info("Alert sent.");
    }

    private void ensurePhoneNumberExists(String phoneNumber) {
        //Can be improved by fetching next valid developer who has valid phone number
        if(StringUtils.isBlank(phoneNumber)){
            throw new ApiException("Invalid phone number "+phoneNumber+ "found for developer, alert cannot be sent",HttpStatus.PRECONDITION_FAILED);
        }
    }

    private Developer chooseAnyDeveloper(List<Developer> developerList) {
        int index = new Random().nextInt(developerList.size());
        Developer developer = developerList.get(index);
        return developer;
    }
}

package com.demo.ams.service.impl;

import com.demo.ams.dto.CreateTeamRequest;
import com.demo.ams.dto.CreateTeamResponse;
import com.demo.ams.dto.DeveloperDetail;
import com.demo.ams.entity.Developer;
import com.demo.ams.entity.Team;
import com.demo.ams.repository.TeamRepository;
import com.demo.ams.service.TeamService;
import com.demo.ams.service.validation.TeamValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamValidation teamValidation;

    @Override
    public CreateTeamResponse createTeam(CreateTeamRequest createTeam) {
        LOGGER.info("Creating team");
        teamValidation.validateTeamCreationRequest(createTeam);
        Team team = buildTeam(createTeam.getTeamName());
        addDevelopersToTeam(team, createTeam.getDevelopers());
        team = saveTeam(team);
        CreateTeamResponse response = new CreateTeamResponse();
        response.setCreatedTeamId(team.getId());
        LOGGER.info("Created team, teamId {}",response.getCreatedTeamId());
        return response;
    }

    @Override
    public Optional<Team> getTeamById(Long teamId) {
        return teamRepository.findById(teamId);
    }

    private Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    private void addDevelopersToTeam(Team team, List<DeveloperDetail> developers) {
        List<Developer> developerList = developers.stream().map(developerDetail -> buildDeveloper(team, developerDetail)).collect(Collectors.toList());
        team.setDevelopers(developerList);
    }

    private Developer buildDeveloper(Team team, DeveloperDetail developerDetail) {
        Developer developer = new Developer();
        developer.setFullName(developerDetail.getName());
        developer.setPhoneNumber(developerDetail.getPhoneNumber());
        developer.setTeam(team);
        return developer;
    }

    private Team buildTeam(String teamName) {
        Team team = new Team();
        team.setTeamName(teamName);
        return team;
    }
}

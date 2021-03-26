package com.demo.ams.service;

import com.demo.ams.dto.CreateTeamRequest;
import com.demo.ams.dto.CreateTeamResponse;
import com.demo.ams.entity.Team;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TeamService {
    @Transactional
    CreateTeamResponse createTeam(CreateTeamRequest createTeam);

    Optional<Team> getTeamById(Long teamId);
}

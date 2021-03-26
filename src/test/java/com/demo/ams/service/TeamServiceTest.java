package com.demo.ams.service;

import com.demo.ams.dto.CreateTeamRequest;
import com.demo.ams.dto.CreateTeamResponse;
import com.demo.ams.dto.DeveloperDetail;
import com.demo.ams.entity.Team;
import com.demo.ams.exception.ApiException;
import com.demo.ams.repository.TeamRepository;
import com.demo.ams.service.impl.TeamServiceImpl;
import com.demo.ams.service.validation.TeamValidation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeamServiceTest {

    @InjectMocks
    private TeamServiceImpl teamService;

    @Mock
    private TeamValidation teamValidation;

    @Mock
    private TeamRepository teamRepository;

    @BeforeAll
    public void setup(){
        initMocks(this);
    }

    @Test
    public void shouldSaveTeam(){
        Team team = new Team();
        team.setId(1L);
        when(teamRepository.save(any(Team.class))).thenReturn(team);
        CreateTeamRequest createTeamRequest = new CreateTeamRequest();
        createTeamRequest.setTeamName("aa");
        DeveloperDetail developer = new DeveloperDetail();
        developer.setPhoneNumber("11111111");
        developer.setName("test dev");
        createTeamRequest.setDevelopers(Arrays.asList(developer));
        when(teamValidation.validateTeamCreationRequest(createTeamRequest)).thenCallRealMethod();

        CreateTeamResponse createTeamResponse = teamService.createTeam(createTeamRequest);

        verify(teamRepository, atLeast(1)).save(any(Team.class));
        assertTrue(team.getId().equals(createTeamResponse.getCreatedTeamId()));
    }

    @Test
    void shouldThrowExceptionIfTeamHasNoDeveloper() {
        Team team = new Team();
        team.setId(1L);
        when(teamRepository.save(any(Team.class))).thenReturn(team);
        CreateTeamRequest createTeamRequest = new CreateTeamRequest();
        createTeamRequest.setTeamName("xc");
        when(teamValidation.validateTeamCreationRequest(createTeamRequest)).thenCallRealMethod();

        Exception exception = assertThrows(ApiException.class, () ->
                teamService.createTeam(createTeamRequest));

        assertEquals("Team cannot be created without developer, please add atleast one developer to it.", exception.getMessage());
    }
}

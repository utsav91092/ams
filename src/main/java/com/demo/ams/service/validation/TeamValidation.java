package com.demo.ams.service.validation;

import com.demo.ams.dto.CreateTeamRequest;
import com.demo.ams.dto.DeveloperDetail;
import com.demo.ams.exception.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class TeamValidation {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamValidation.class);

    public boolean validateTeamCreationRequest(CreateTeamRequest createTeamRequest){
        boolean isValid = true;
        LOGGER.info("Validating team");
        if(StringUtils.isBlank(createTeamRequest.getTeamName())){
            isValid = false;
            throw new ApiException("Team name must not be empty, Please provide valid team name", HttpStatus.PRECONDITION_FAILED);
        }
        if(CollectionUtils.isEmpty(createTeamRequest.getDevelopers())){
            isValid = false;
            throw new ApiException("Team cannot be created without developer, please add atleast one developer to it.", HttpStatus.PRECONDITION_FAILED);
        }
        validateDeveloperDetails(createTeamRequest.getDevelopers());
        return isValid;
    }

    private void validateDeveloperDetails(List<DeveloperDetail> developers) {
        long invalidDeveloperDetailCount = developers.stream().filter(developerDetail -> StringUtils.isBlank(developerDetail.getName()) || StringUtils.isBlank(developerDetail.getPhoneNumber())).count();
        if(invalidDeveloperDetailCount > 0){
            throw new ApiException("Phone Number or Name cannot be empty for developer",HttpStatus.PRECONDITION_FAILED);
        }
    }
}

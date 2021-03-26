package com.demo.ams.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateTeamRequest {
    @JsonProperty("teamName")
    private String teamName;
    @JsonProperty("developers")
    private List<DeveloperDetail> developers;
}

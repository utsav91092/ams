package com.demo.ams.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendSmsRequest {
    @JsonProperty("phone_number")
    private String phoneNumber;
}

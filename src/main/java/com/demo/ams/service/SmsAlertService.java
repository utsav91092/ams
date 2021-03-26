package com.demo.ams.service;

import com.demo.ams.dto.SendSmsRequest;
import com.demo.ams.entity.Developer;
import com.demo.ams.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsAlertService implements Alert{

    @Value("${endpoint.send.sms}")
    private String sendSmsEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void alert(Developer developer) {
        try {
            restTemplate.postForObject(sendSmsEndpoint,buildRequest(developer), Object.class);
        }catch (Exception ex){
            throw new ApiException("Failed to send alert to the developer", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private SendSmsRequest buildRequest(Developer developer) {
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumber(developer.getPhoneNumber());
        return sendSmsRequest;
    }
}

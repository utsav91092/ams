package com.demo.ams.service;

import com.demo.ams.entity.Developer;
import com.demo.ams.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class EmailAlertService implements Alert{
    @Override
    public void alert(Developer developer) {
        throw new ApiException("Email alert not supported", HttpStatus.NOT_IMPLEMENTED);
    }
}

package com.demo.ams.service;

import com.demo.ams.AlertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AlertFactory {

    @Autowired
    private SmsAlertService smsAlertService;
    @Autowired
    private EmailAlertService emailAlertService;

    public Alert getAlertService(AlertType alertType) {
        if (alertType == null) {
            throw new IllegalArgumentException("Invalid " + alertType);
        }
        Alert service = null;
        switch (alertType) {
            case SMS:
                service = smsAlertService;
                break;
            case EMAIL:
                service = emailAlertService;
                break;
            default:
                throw new NoSuchElementException("Error in getting alert service for : "+alertType);
        }
        return service;
    }
}

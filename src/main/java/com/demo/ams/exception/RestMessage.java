package com.demo.ams.exception;

import lombok.Getter;

@Getter
public class RestMessage {
    private String message;
    public RestMessage(String message) {
        this.message = message;
    }
}

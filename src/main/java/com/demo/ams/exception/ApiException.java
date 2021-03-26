package com.demo.ams.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException extends RuntimeException {

    private String errorCode;
    private HttpStatus statusCode;
    private Object[] args;

    public ApiException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ApiException(String message, HttpStatus statusCode) {
        this(message, statusCode, null);
    }
    public ApiException(String message, HttpStatus statusCode, String errorCode) {
        this(message, statusCode, errorCode, null);
    }

    public ApiException(String message, HttpStatus statusCode, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }


    public ApiException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public ApiException(String message, Object[] args, HttpStatus statusCode) {
        this(message, statusCode, null);
        this.errorCode = message;
        this.args = args;
    }
}

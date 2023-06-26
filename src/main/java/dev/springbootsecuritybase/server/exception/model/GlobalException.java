package dev.springbootsecuritybase.server.exception.model;

import org.springframework.http.HttpStatus;


public class GlobalException extends RuntimeException {
    private HttpStatus httpStatus;
    
    public GlobalException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

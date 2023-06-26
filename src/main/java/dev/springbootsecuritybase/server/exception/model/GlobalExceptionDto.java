package dev.springbootsecuritybase.server.exception.model;

import java.time.Instant;

public class GlobalExceptionDto {
    private String message;
    private Instant timestamp = Instant.now();

    public GlobalExceptionDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}

package com.ecomm.comms.exception;

import lombok.Data;

import java.time.Instant;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now().toEpochMilli();
    }

}
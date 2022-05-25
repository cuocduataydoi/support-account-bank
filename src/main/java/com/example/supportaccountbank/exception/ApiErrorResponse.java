package com.example.supportaccountbank.exception;

import lombok.Data;

import java.time.Instant;

@Data
public class ApiErrorResponse {
    private String errorCode;
    private String message;
    private String timestamp;

    public ApiErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = Instant.now().toString();
    }
}

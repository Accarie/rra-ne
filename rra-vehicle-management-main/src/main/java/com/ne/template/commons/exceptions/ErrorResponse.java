package com.ne.template.commons.exceptions;

import java.time.LocalDateTime;


public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResponse(LocalDateTime now, int status, String error, String message) {
        this.timestamp = LocalDateTime.now();  // Correct way to get current time
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }





}

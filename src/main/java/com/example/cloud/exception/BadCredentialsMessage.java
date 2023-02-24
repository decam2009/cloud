package com.example.cloud.exception;

import lombok.Data;

@Data
public class BadCredentialsMessage {

    private final String message;
    private final Integer id;

    public BadCredentialsMessage(String message, Integer id) {
        this.message = message;
        this.id = id;
    }
}

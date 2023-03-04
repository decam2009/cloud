package com.example.cloud.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorMessage extends RuntimeException {
    private final HttpStatus status;

    public ErrorMessage(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

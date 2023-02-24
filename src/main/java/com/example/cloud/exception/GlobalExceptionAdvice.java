package com.example.cloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionAdvice {

    private static final String BAD_CREDENTIALS = "Bad credentials";
    private static final Integer ID = 0;
    @ExceptionHandler ({NullPointerException.class, NoSuchElementException.class})
    public ResponseEntity<BadCredentialsMessage> catchBadRequestException() {
        BadCredentialsMessage message = new BadCredentialsMessage(BAD_CREDENTIALS, ID);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}

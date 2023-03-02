package com.example.cloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionAdvice {

    private static final String BAD_REQUEST = "No such element or null pointer exception";

    private static final String UNAUTHORIZED = "Unauthorized error";

    private static final String INTERNAL = "Unauthorized error";


    @ExceptionHandler({NullPointerException.class, NoSuchElementException.class})
    public ResponseEntity<ErrorMessage> catchBadRequestException() {
        ErrorMessage message = new ErrorMessage(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Unauthorized.class})
    public ResponseEntity<ErrorMessage> catchUnauthorizedException() {
        ErrorMessage message = new ErrorMessage(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({InternalServerError.class})
    public ResponseEntity<ErrorMessage> catchInternalException() {
        ErrorMessage message = new ErrorMessage(INTERNAL, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

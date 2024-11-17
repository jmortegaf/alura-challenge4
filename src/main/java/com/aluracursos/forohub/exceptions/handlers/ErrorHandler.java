package com.aluracursos.forohub.exceptions.handlers;

import com.aluracursos.forohub.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Stream;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(InvalidUserRegisterDataException.class)
    public ResponseEntity<?> invalidUserRegisterDataHandler(InvalidUserRegisterDataException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error","Bad Request","message", e.getMessage()));
    }

    @ExceptionHandler(UserAuthenticationErrorException.class)
    public ResponseEntity<?> userAuthenticationErrorHandler(UserAuthenticationErrorException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error","Unauthorized",
                        "message",e.getMessage()));
    }

    @ExceptionHandler(InvalidThreadStatusException.class)
    public ResponseEntity<?> invalidThreadStatusHandler(InvalidThreadStatusException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error","Unauthorized","message",e.getMessage()));
    }

    @ExceptionHandler(InvalidReplyException.class)
    public ResponseEntity<?> invalidReplyHandler(InvalidReplyException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error","Bad Request","message",e.getMessage()));
    }

}

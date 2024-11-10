package com.aluracursos.forohub.exceptions.handlers;

import com.aluracursos.forohub.exceptions.InvalidUserRegisterDataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Stream;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Stream<ErrorData>> error400Handler(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getFieldErrors()
                .stream().map(ErrorData::new));
    }

    @ExceptionHandler(InvalidUserRegisterDataException.class)
    public ResponseEntity<String> invalidUserRegisterDataHandler(InvalidUserRegisterDataException e){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    }

    public record ErrorData(String field,String msg){
        public ErrorData(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}

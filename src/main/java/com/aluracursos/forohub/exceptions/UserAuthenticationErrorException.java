package com.aluracursos.forohub.exceptions;

public class UserAuthenticationErrorException extends RuntimeException{

    public UserAuthenticationErrorException(String message){
        super(message);
    }
}

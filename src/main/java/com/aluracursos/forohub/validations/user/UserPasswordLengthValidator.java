package com.aluracursos.forohub.validations.user;

import com.aluracursos.forohub.dto.UserRegisterData;
import com.aluracursos.forohub.exceptions.InvalidUserRegisterDataException;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordLengthValidator implements UserRegisterDataValidator{

    @Override
    public void validate(UserRegisterData userRegisterData) {
        if(userRegisterData.password().length()<16)
            throw new InvalidUserRegisterDataException("Password must be at least 16 characters long");

    }
}

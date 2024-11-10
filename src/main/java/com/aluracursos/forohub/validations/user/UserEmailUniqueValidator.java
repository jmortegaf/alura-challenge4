package com.aluracursos.forohub.validations.user;

import com.aluracursos.forohub.dto.UserRegisterData;
import com.aluracursos.forohub.exceptions.InvalidUserRegisterDataException;
import com.aluracursos.forohub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEmailUniqueValidator implements UserRegisterDataValidator{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(UserRegisterData userRegisterData) {
        var email=userRepository.existByEmail(userRegisterData.email());
        if(email) throw new InvalidUserRegisterDataException("Email: "+userRegisterData.email()+" already in use");
    }
}

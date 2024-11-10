package com.aluracursos.forohub.services;

import com.aluracursos.forohub.dto.UserRegisterData;
import com.aluracursos.forohub.models.User;
import com.aluracursos.forohub.repository.UserRepository;
import com.aluracursos.forohub.validations.user.UserRegisterDataValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegisterService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private List<UserRegisterDataValidator> userRegisterDataValidators;

    public UserRegisterData registerNewUser(@Valid UserRegisterData userRegisterData) {
        var hashedPassword=passwordEncoder.encode(userRegisterData.password());

        userRegisterDataValidators.forEach(validator->validator.validate(userRegisterData));

        userRepository.save(new User(userRegisterData,hashedPassword));
        return new UserRegisterData(userRegisterData,true);
    }
}

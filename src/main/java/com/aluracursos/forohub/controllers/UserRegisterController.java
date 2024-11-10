package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.dto.UserRegisterData;
import com.aluracursos.forohub.models.User;
import com.aluracursos.forohub.repository.UserRepository;
import com.aluracursos.forohub.services.UserRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class UserRegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRegisterService userRegisterService;


    @PostMapping
    public ResponseEntity<UserRegisterData> registerUser(@RequestBody @Valid UserRegisterData userRegisterData){
        var user=userRegisterService.registerNewUser(userRegisterData);
        return ResponseEntity.ok(user);
    }

}

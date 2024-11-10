package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.dto.UserRegisterData;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity getUsers(){
        return ResponseEntity.ok().build();
    }

    @PostMapping ResponseEntity<UserRegisterData> registerUser(@RequestBody @Valid UserRegisterData data){
        System.out.println(data);
        return ResponseEntity.ok().build();
    }

}

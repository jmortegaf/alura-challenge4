package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.dto.JWTTokenData;
import com.aluracursos.forohub.dto.UserAuthenticationData;
import com.aluracursos.forohub.models.User;
import com.aluracursos.forohub.services.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@SecurityRequirement(name = "bearer-key")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<JWTTokenData> authenticateUser(@RequestBody @Valid UserAuthenticationData userAuthenticationData){
        Authentication authToken=new UsernamePasswordAuthenticationToken(
                userAuthenticationData.userName(),userAuthenticationData.password());
        var authenticatedUser=authenticationManager.authenticate(authToken);
        var JWTToken=tokenService.generateToken((User)authenticatedUser.getPrincipal());
        return ResponseEntity.ok(new JWTTokenData(JWTToken));
    }
}

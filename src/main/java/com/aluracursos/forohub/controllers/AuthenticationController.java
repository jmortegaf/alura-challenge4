package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.dto.JWTTokenData;
import com.aluracursos.forohub.dto.UserAuthenticationData;
import com.aluracursos.forohub.models.User;
import com.aluracursos.forohub.services.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
@SecurityRequirement(name = "bearer-key")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid UserAuthenticationData userAuthenticationData) {
        System.out.println(LocaleContextHolder.getLocale());
        try {
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    userAuthenticationData.userName(), userAuthenticationData.password());
            System.out.println(authToken);
            var authenticatedUser = authenticationManager.authenticate(authToken);
            var JWTToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
            return ResponseEntity.ok(new JWTTokenData(JWTToken));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }
}

package com.aluracursos.forohub.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterData(
        @NotBlank String userName,
        @NotBlank String email,
        @NotBlank String password) {
    public UserRegisterData(@Valid UserRegisterData userRegisterData, boolean hidePassword) {
        this(userRegisterData.userName(), userRegisterData.email(), "*".repeat(16));
    }
}

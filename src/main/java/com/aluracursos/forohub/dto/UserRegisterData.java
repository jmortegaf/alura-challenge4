package com.aluracursos.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterData(
        @NotBlank String userName,
        @NotBlank String email,
        @NotBlank String password) {}

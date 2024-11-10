package com.aluracursos.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationData(
        @NotBlank String userName,
        @NotBlank String password){}

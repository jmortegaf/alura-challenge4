package com.aluracursos.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateThreadData(
        @NotBlank String title,
        @NotBlank String message) {}

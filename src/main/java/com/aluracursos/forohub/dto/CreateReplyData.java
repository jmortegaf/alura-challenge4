package com.aluracursos.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateReplyData(
        @NotBlank String message) {}

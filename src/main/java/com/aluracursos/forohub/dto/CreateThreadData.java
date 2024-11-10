package com.aluracursos.forohub.dto;

import com.aluracursos.forohub.models.Thread;
import com.aluracursos.forohub.models.ThreadStatus;

import java.time.LocalDateTime;

public record CreateThreadData(
        String title,
        String message) {}

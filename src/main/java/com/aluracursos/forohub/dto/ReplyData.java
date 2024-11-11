package com.aluracursos.forohub.dto;

import com.aluracursos.forohub.models.Reply;

import java.time.LocalDateTime;

public record ReplyData(
        String message,
        String author,
        LocalDateTime creationDate) {
    public ReplyData(Reply reply) {
        this(reply.getMessage(), reply.getAuthor().getUsername(),reply.getCreationDate());
    }
}

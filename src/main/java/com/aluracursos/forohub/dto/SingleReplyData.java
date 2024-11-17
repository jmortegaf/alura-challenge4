package com.aluracursos.forohub.dto;

import com.aluracursos.forohub.models.Reply;

import java.time.LocalDateTime;
import java.util.List;

public record SingleReplyData(
        Long id,
        String message,
        String author,
       LocalDateTime creationDate) {

    public SingleReplyData(Reply reply) {
        this(reply.getId(),
                reply.isDeleted()?"[DELETED]":reply.getMessage(),
                reply.getAuthor().getUsername(),reply.getCreationDate());
    }
}

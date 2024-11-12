package com.aluracursos.forohub.dto;

import com.aluracursos.forohub.models.Reply;

import java.time.LocalDateTime;
import java.util.List;

public record ReplyData(
        Long id,
        String message,
        String author,
        LocalDateTime creationDate,
        List<ReplyData> childReplies) {
    public ReplyData(Reply reply) {
        this(reply.getId(), reply.getMessage(), reply.getAuthor().getUsername(),reply.getCreationDate(),
                reply.getChildReplies().stream().map(ReplyData::new).toList());
    }
}

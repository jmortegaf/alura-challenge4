package com.aluracursos.forohub.dto;

import com.aluracursos.forohub.models.Reply;
import com.aluracursos.forohub.models.Thread;
import com.aluracursos.forohub.models.ThreadStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public record FullThreadData(
        Long id,
        String title,
        String message,
        LocalDateTime creationDate,
        ThreadStatus status,
        String author,
        Integer replyCount,
        Page<ReplyData> replies) {


    public FullThreadData(Thread thread, Page<Reply> repliesPage) {
        this(thread.getId(), thread.getTitle(), thread.getMessage(), thread.getCreationDate(),
                thread.getStatus(),thread.getAuthor().getUsername(),thread.getReplyCount(),
                repliesPage.map(ReplyData::new));

    }
}

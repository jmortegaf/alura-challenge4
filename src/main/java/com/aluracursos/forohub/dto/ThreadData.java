package com.aluracursos.forohub.dto;

import com.aluracursos.forohub.models.Thread;
import com.aluracursos.forohub.models.ThreadStatus;

import java.time.LocalDateTime;

public record ThreadData(
        Long id,
        String title,
        String messageFragment,
        LocalDateTime creationDate,
        ThreadStatus status,
        String author,
        Integer replyCount) {

    public ThreadData(Thread thread){
        this(thread.getId(),thread.getTitle(),
                thread.getMessage().length()>64?thread.getMessage().substring(0,64)+"...": thread.getMessage(),
                thread.getCreationDate(),thread.getStatus(),thread.getAuthor().getUsername(),
                thread.getReplyCount());
    }

}

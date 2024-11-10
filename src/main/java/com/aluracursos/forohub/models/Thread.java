package com.aluracursos.forohub.models;

import java.time.LocalDateTime;
import java.util.List;

public class Thread {

    private Long id;
    private String title;
    private String message;
    private LocalDateTime creationDate;
    private ThreadStatus status;
    private User author;
    private Course course;
    private List<Reply> replies;

}

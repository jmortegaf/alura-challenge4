package com.aluracursos.forohub.models;

import java.time.LocalDateTime;

public class Reply {

    private Long id;
    private String message;
    private Thread thread;
    private LocalDateTime creationDate;
    private User author;

}

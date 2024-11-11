package com.aluracursos.forohub.models;

import com.aluracursos.forohub.dto.CreateReplyData;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id")
    private Thread thread;

    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    public Reply(CreateReplyData replyData, Thread thread,User user) {
        this.message=replyData.message();
        this.thread=thread;
        this.creationDate=LocalDateTime.now();
        this.author=user;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", author=" + author +
                ", creationDate=" + creationDate +
                '}';
    }
}

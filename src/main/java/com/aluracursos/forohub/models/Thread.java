package com.aluracursos.forohub.models;

import com.aluracursos.forohub.dto.CreateThreadData;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "thread")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private ThreadStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;
//    private Course course;
    @OneToMany(mappedBy = "thread")
    private List<Reply> replies;

    public Thread(CreateThreadData data, User author) {
        this.title=data.title();
        this.message=data.message();
        this.creationDate=LocalDateTime.now();
        this.status=ThreadStatus.ACTIVE;
        this.author=author;
        this.replies=new ArrayList<>();
    }
}

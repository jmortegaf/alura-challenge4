package com.aluracursos.forohub.models;

import com.aluracursos.forohub.dto.CreateReplyData;
import com.aluracursos.forohub.dto.CreateThreadData;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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

    @Column(unique = true)
    private String title;
    @Column(unique = true)
    private String message;
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private ThreadStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;
//    private Course course;
    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
    private List<Reply> replies;
    private Integer replyCount;

    public Thread(CreateThreadData data, User author) {
        this.title=data.title();
        this.message=data.message();
        this.creationDate=LocalDateTime.now();
        this.status=ThreadStatus.ACTIVE;
        this.author=author;
        this.replies=new ArrayList<>();
        this.replyCount=0;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", creationDate=" + creationDate +
                ", status=" + status +
                ", author=" + author +
                ", replies=" + replies +
                '}';
    }

    public void addReply(CreateReplyData replyData, User user) {
        this.replies.add(new Reply(replyData,this,user));
        this.replyCount+=1;
    }
    public void addReplyToReply(@Valid CreateReplyData createReplyData, Reply reply,User user) {
        var newReply=new Reply(createReplyData,this,reply,user);
        reply.addReply(newReply);
        this.replies.add(newReply);
        this.replyCount+=1;
    }

    public void setDeleted() {
        this.title="[Deleted]";
        this.message="[Deleted]";
        this.replyCount=0;
    }

    public void update(CreateThreadData createThreadData) {
        this.title= createThreadData.title()==null?this.title: createThreadData.title();
        this.message= createThreadData.message()==null?this.message: createThreadData.message();
    }
}

package com.aluracursos.forohub.models;

import com.aluracursos.forohub.dto.CreateReplyData;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "parent_reply_id")
    private Reply parentReply;

    @OneToMany(mappedBy = "parentReply",cascade = CascadeType.ALL)
    private List<Reply> childReplies;
    private Integer replyCount;

    private boolean deleted=false;



    public Reply(CreateReplyData replyData, Thread thread,User user) {
        this.message=replyData.message();
        this.thread=thread;
        this.creationDate=LocalDateTime.now();
        this.author=user;
        this.childReplies=new ArrayList<>();
        this.replyCount=0;
        this.deleted=false;
    }

    public Reply(@Valid CreateReplyData createReplyData, Thread thread, Reply reply, User user) {
        this.message=createReplyData.message();
        this.thread=thread;
        this.creationDate=LocalDateTime.now();
        this.author=user;
        this.parentReply=reply;
        this.childReplies=new ArrayList<>();
        this.replyCount=0;
        this.deleted=false;
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

    public void setDeleted() {
        this.message="[Deleted]";
    }

    public void addReply(Reply newReply) {
        this.childReplies.add(newReply);
        this.replyCount+=1;
    }

    public boolean hasChildren() {
        return replyCount != 0;
    }

    public boolean hasParent() {
        return parentReply!=null;
    }

}

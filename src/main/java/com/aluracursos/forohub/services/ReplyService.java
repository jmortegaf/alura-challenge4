package com.aluracursos.forohub.services;


import com.aluracursos.forohub.components.UtilsComponent;
import com.aluracursos.forohub.dto.CreateReplyData;
import com.aluracursos.forohub.dto.SingleReplyData;
import com.aluracursos.forohub.exceptions.InvalidReplyException;
import com.aluracursos.forohub.exceptions.InvalidThreadStatusException;
import com.aluracursos.forohub.exceptions.UserAuthenticationErrorException;
import com.aluracursos.forohub.models.Reply;
import com.aluracursos.forohub.models.ThreadStatus;
import com.aluracursos.forohub.repository.ReplyRepository;
import com.aluracursos.forohub.repository.ThreadRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private UtilsComponent utilsComponent;

    // edit a reply by its id
    @Transactional
    public Map<String,String> editReply(@Valid Long id, CreateReplyData createReplyData) {
        var reply=replyRepository.findById(id);
        if(reply.isPresent()){
            if(isReplyOwner(reply.get())){
                reply.get().setMessage(createReplyData.message());
                return Map.of("status","success",
                        "message","Reply updated successfully");
            }
            throw new UserAuthenticationErrorException("You are not the reply author to perform this task");
        }
        throw new NoSuchElementException("No reply with id:"+id);
    }

    @Transactional
    public Map<String, String> deleteReply(@Valid Long id) {
        var reply=replyRepository.findById(id);
        if(reply.isPresent()){
            if(isReplyOwner(reply.get())){
                reply.get().setDeleted(true);
                return Map.of("status","success",
                        "message","Reply deleted successfully");
            }
            throw new UserAuthenticationErrorException("You are not the reply author to perform this task");
        }
        throw new NoSuchElementException("No reply with id:"+id);
    }

    // Get a single reply
    public SingleReplyData getReply(@Valid Long id) {
        var reply=replyRepository.findById(id);
        if(reply.isPresent())return new SingleReplyData(reply.get());
        throw new NoSuchElementException("No reply with id:"+id);
    }


    // Reply to a Reply (its only allowed to reply a thread reply, but a reply's reply cannot be replied xd)
    @Transactional
    public Map<String, String> replyToReply(@Valid Long id, @Valid CreateReplyData createReplyData) {
        var parentReply=replyRepository.findById(id);
        if(parentReply.isPresent()){
            if(parentReply.get().hasParent())throw new InvalidReplyException("Maximum reply depth is 2");
            var thread=threadRepository.findById(parentReply.get().getThread().getId());
            var user=utilsComponent.getUser();
            if(thread.isPresent()){
                if(!thread.get().getStatus().equals(ThreadStatus.ACTIVE))
                    throw new InvalidThreadStatusException("Error creating reply, thread is "+thread.get().getStatus());
                thread.get().addReplyToReply(createReplyData,parentReply.get(),user);
                return Map.of("status","success","message","Reply created successfully");
            }
            throw new NoSuchElementException("No thread with id:"+parentReply.get().getAuthor().getId());
        }
        throw new NoSuchElementException("No reply with id:"+id);
    }

    private Boolean isReplyOwner(Reply reply) {
        var user=utilsComponent.getUser();
        return reply.getAuthor().equals(user);
    }
}

package com.aluracursos.forohub.services;

import com.aluracursos.forohub.components.UtilsComponent;
import com.aluracursos.forohub.dto.CreateReplyData;
import com.aluracursos.forohub.dto.CreateThreadData;
import com.aluracursos.forohub.dto.FullThreadData;
import com.aluracursos.forohub.dto.ThreadData;
import com.aluracursos.forohub.exceptions.InvalidThreadStatusException;
import com.aluracursos.forohub.exceptions.UserAuthenticationErrorException;
import com.aluracursos.forohub.models.Reply;
import com.aluracursos.forohub.models.Thread;
import com.aluracursos.forohub.models.ThreadStatus;
import com.aluracursos.forohub.models.User;
import com.aluracursos.forohub.repository.ReplyRepository;
import com.aluracursos.forohub.repository.ThreadRepository;
import com.aluracursos.forohub.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ThreadService {

    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private UtilsComponent utilsComponent;

    // return all threads in a pageable object
    public Page<Thread> getThreads(Pageable pageable) {
        var pageData = threadRepository.findAll(pageable);
        pageData.forEach(thread -> {
            if (thread.getStatus().equals(ThreadStatus.DELETED)) {
                thread.setDeleted();
            }
        });
        return pageData;
    }

    // create a new thread
    public ThreadData createThread(CreateThreadData createThreadData) {
        var user = utilsComponent.getUser();
        var thread = threadRepository.save(new Thread(createThreadData, user));
        return new ThreadData(thread);
    }

    // Reply to an active thread
    @Transactional
    public Map<String,String> replyThread(Long id, CreateReplyData createReplyData) {
        var user = utilsComponent.getUser();
        var thread = threadRepository.findById(id);
        if(thread.isPresent()){
            if (!thread.get().getStatus().equals(ThreadStatus.ACTIVE))
                throw new InvalidThreadStatusException("Error creating reply, thread is " + thread.get().getStatus());
            thread.get().addReply(createReplyData, user);
            return Map.of("status","success",
                    "message","Reply added successfully");
        }
        throw new NoSuchElementException("No thread with id:"+id);
    }

    // Get Full Thread
    public FullThreadData getThread(Long id, Pageable pageable){
        var thread = threadRepository.findById(id);
        if(thread.isPresent()){
            Page<Reply> repliesPage = replyRepository.findByThreadIdAndParentReplyIsNull(thread.get().getId(), pageable);
            if (thread.get().getStatus().equals(ThreadStatus.DELETED)) {
                thread.get().setDeleted();
                repliesPage.forEach(Reply::setDeleted);
            }
            return new FullThreadData(thread.get(), repliesPage);
        }
        throw new NoSuchElementException("No thread with id:"+id);
    }

    // Mark a thread as closed
    @Transactional
    public Map<String,String> closeThread(Long id) {
        var thread = threadRepository.findById(id);
        if(thread.isPresent()){
            if (isThreadOwner(thread.get())) {
                thread.get().setStatus(ThreadStatus.CLOSED);
                return Map.of("status","success",
                "message","Thread closed successfully");
            }
            throw new UserAuthenticationErrorException("You are neither the thread author nor an admin to perform this task");
        }
        throw new NoSuchElementException("No thread with id:"+id);
    }

    // Mark a thread as deleted
    @Transactional
    public Map<String,String> deleteThread(Long id) {
        var thread = threadRepository.findById(id);
        if(thread.isPresent()){
            if (isThreadOwner(thread.get())) {
                thread.get().setStatus(ThreadStatus.DELETED);
                return Map.of("status","success",
                        "message","Thread deleted successfully");
            }
            throw new UserAuthenticationErrorException("You are neither the thread author nor an admin to perform this task");
        }
        throw new NoSuchElementException("No thread with id:"+id);
    }

    // Update the content of a thread
    @Transactional
    public Map<String, String> updateThread(@Valid Long id, CreateThreadData createThreadData) {
        var thread = threadRepository.findById(id);
        if(thread.isPresent()){
            if (isThreadOwner(thread.get())) {
                thread.get().update(createThreadData);
                return Map.of("status","success",
                        "message","Thread updated successfully");
            }
            throw new UserAuthenticationErrorException("You are neither the thread author nor an admin to perform this task");
        }
        throw new NoSuchElementException("No thread with id:"+id);
    }

    // Check if the request was sent by the thread owner
    private Boolean isThreadOwner(Thread thread) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            var user = (User) userRepository.findByUserName(userDetails.getUsername());
            return thread.getAuthor().equals(user);
        }
        return false;
    }
}
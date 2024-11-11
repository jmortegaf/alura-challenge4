package com.aluracursos.forohub.services;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {

    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReplyRepository replyRepository;

    public Page<Thread> getThreads(Pageable pageable) {
        var pageData=threadRepository.findAll(pageable);
        pageData.forEach(thread -> {
            if(thread.getStatus().equals(ThreadStatus.DELETED)){
                thread.setDeleted();
            }
        });
        return pageData;
    }

    public ThreadData createThread(CreateThreadData createThreadData) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof UserDetails userDetails){
            var user=(User)userRepository.findByUserName(userDetails.getUsername());
            var thread=threadRepository.save(new Thread(createThreadData,user));
            return new ThreadData(thread);
        }
        throw new UserAuthenticationErrorException("User authentication failed.");
    }

    @Transactional
    public FullThreadData replyThread(Long id, CreateReplyData createReplyData, Pageable pageable){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof UserDetails userDetails){
            var user=(User)userRepository.findByUserName(userDetails.getUsername());
            var thread=threadRepository.findById(id).get();
            if(!thread.getStatus().equals(ThreadStatus.ACTIVE))
                throw new InvalidThreadStatusException("Error creating reply thread is "+thread.getStatus());
            thread.addReply(createReplyData,user);
            threadRepository.save(thread);
            Page<Reply> repliesPage=replyRepository.findByThreadId(thread.getId(),pageable);
            return new FullThreadData(thread,repliesPage);
        }
        throw new UserAuthenticationErrorException("User authentication failed.");
    }

    public FullThreadData getThread(Long id, Pageable pageable) {
        var thread=threadRepository.findById(id).get();
        Page<Reply> repliesPage=replyRepository.findByThreadId(thread.getId(),pageable);

        if(thread.getStatus().equals(ThreadStatus.DELETED)){
            thread.setDeleted();
            repliesPage.forEach(Reply::setDeleted);
        }

        return new FullThreadData(thread,repliesPage);
    }

    @Transactional
    public ThreadData closeThread(Long id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof UserDetails userDetails){
            var user=(User)userRepository.findByUserName(userDetails.getUsername());
            var thread=threadRepository.findById(id).get();
            if(thread.getAuthor().equals(user)) {
                thread.setStatus(ThreadStatus.CLOSED);
                return new ThreadData(thread);
            }
        }
        throw new UserAuthenticationErrorException("You are neither the thread author nor an admin to perform this task");
    }

    @Transactional
    public ThreadData deleteThread(Long id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            var user = (User) userRepository.findByUserName(userDetails.getUsername());
            var thread = threadRepository.findById(id).get();
            if (thread.getAuthor().equals(user)) {
                thread.setStatus(ThreadStatus.DELETED);
                return new ThreadData(thread);
            }
        }
        throw new UserAuthenticationErrorException("You are neither the thread author nor an admin to perform this task");
    }
}

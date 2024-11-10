package com.aluracursos.forohub.services;

import com.aluracursos.forohub.dto.CreateThreadData;
import com.aluracursos.forohub.dto.ThreadData;
import com.aluracursos.forohub.exceptions.UserAuthenticationErrorException;
import com.aluracursos.forohub.models.Thread;
import com.aluracursos.forohub.models.User;
import com.aluracursos.forohub.repository.ThreadRepository;
import com.aluracursos.forohub.repository.UserRepository;
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

    public Page<Thread> getThreads(Pageable pageable) {
        return threadRepository.findAll(pageable);
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
}

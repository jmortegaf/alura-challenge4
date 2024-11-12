package com.aluracursos.forohub.services;


import com.aluracursos.forohub.dto.CreateReplyData;
import com.aluracursos.forohub.dto.SingleReplyData;
import com.aluracursos.forohub.dto.ThreadData;
import com.aluracursos.forohub.exceptions.UserAuthenticationErrorException;
import com.aluracursos.forohub.models.ThreadStatus;
import com.aluracursos.forohub.models.User;
import com.aluracursos.forohub.repository.ReplyRepository;
import com.aluracursos.forohub.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReplyRepository replyRepository;


    @Transactional
    public SingleReplyData editReply(@Valid Long id, CreateReplyData createReplyData) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof UserDetails userDetails){
            var user=(User)userRepository.findByUserName(userDetails.getUsername());
            var reply=replyRepository.findById(id).get();
            if(reply.getAuthor().equals(user)) {
                reply.setMessage(createReplyData.message());
                return new SingleReplyData(reply);
            }
        }
        throw new UserAuthenticationErrorException("You are not the reply author to perform this task");

    }

    @Transactional
    public SingleReplyData deleteReply(@Valid Long id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof UserDetails userDetails){
            var user=(User)userRepository.findByUserName(userDetails.getUsername());
            var reply=replyRepository.findById(id).get();
            if(reply.getAuthor().equals(user)) {
                reply.setMessage("[Deleted]");
                return new SingleReplyData(reply);
            }
        }
        throw new UserAuthenticationErrorException("You are not the reply author to perform this task");

    }
}

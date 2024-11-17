package com.aluracursos.forohub.components;

import com.aluracursos.forohub.exceptions.UserAuthenticationErrorException;
import com.aluracursos.forohub.models.User;
import com.aluracursos.forohub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UtilsComponent {

    @Autowired
    private UserRepository userRepository;

    public User getUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return (User) userRepository.findByUserName(userDetails.getUsername());
        }
        throw new UserAuthenticationErrorException("User authentication error");
    }
}

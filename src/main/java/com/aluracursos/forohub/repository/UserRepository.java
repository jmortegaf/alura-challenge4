package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.models.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,Long> {

    UserDetails findByUserName(String userName);

    @Query("select count(u)>0 from User u where u.userName=:userName")
    Boolean existByUserName(String userName);

    @Query("select count(u)>0 from User u where u.email=:email")
    Boolean existByEmail(String email);
}

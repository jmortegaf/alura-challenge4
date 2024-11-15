package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.dto.UserAuthenticationData;
import com.aluracursos.forohub.models.User;
import com.aluracursos.forohub.services.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<UserAuthenticationData> userAuthenticationDataJacksonTester;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private AuthenticationService authenticationService;


    @Test
    @DisplayName("Should return a 400 error response when the request is empty")
    void authenticateUserTest1() throws Exception {
        var response=mockMvc.perform(post("/login"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }
    @Test
    @DisplayName("Should return a 403 error response when the login information is not in the database")
    void authenticateUserTest2() throws Exception {
        var userAuthenticationData=new UserAuthenticationData("user1","password1");
        var response=mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userAuthenticationDataJacksonTester.write(userAuthenticationData).getJson()))
                .andReturn().getResponse();

        System.out.println(response);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());

    }

    @Test
    @DisplayName("Should return a 200 response when the login information is valid")
    void authenticateUserTest3() throws Exception {
        var userAuthenticationData=new UserAuthenticationData("user1","password1");
        when(authenticationService.loadUserByUsername(any())).thenReturn(
                (UserDetails)new User("user1",passwordEncoder.encode("password1")));

        var response=mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userAuthenticationDataJacksonTester.write(
                                new UserAuthenticationData("user1","password1")
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}
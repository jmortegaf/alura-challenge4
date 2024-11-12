package com.aluracursos.forohub.services;

import com.aluracursos.forohub.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationTime;

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                    .withIssuer("alura-forohub")
                    .withSubject(user.getUsername())
                    .withClaim("id",user.getId())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            throw  new RuntimeException();
        }
    }

    public String getSubject(String token){
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("alura-forohub")
                    // reusable verifier instance
                    .build();

            decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            throw new RuntimeException();
        }
    }
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(jwtExpirationTime).toInstant(ZoneOffset.of("-03:00"));
    }
}

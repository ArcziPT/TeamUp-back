package com.arczipt.teamup.security;

import com.arczipt.teamup.service.TeamupUserDetailsService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    public String create(UserDetails userDetails){
        if(userDetails == null)
            throw new BadCredentialsException("User does not exist");

        return JWT.create().withSubject(userDetails.getUsername()).withIssuedAt(new Date(System.currentTimeMillis())).withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRE_TIME)).sign(SecurityConstants.ALGORITHM);
    }

    public String getUsername(String token){
        return JWT.require(SecurityConstants.ALGORITHM).build().verify(token).getSubject();
    }

    public boolean verify(String jwt, UserDetails userDetails){
        DecodedJWT decoded = JWT.require(SecurityConstants.ALGORITHM).withSubject(userDetails.getUsername()).build().verify(jwt);
        return decoded.getExpiresAt().getTime() < System.currentTimeMillis();
    }
}

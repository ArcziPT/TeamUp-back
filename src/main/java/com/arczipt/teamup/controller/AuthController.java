package com.arczipt.teamup.controller;

import com.arczipt.teamup.dto.AuthDTO;
import com.arczipt.teamup.dto.JWTDTO;
import com.arczipt.teamup.security.JWTUtil;
import com.arczipt.teamup.service.TeamupUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private JWTUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private TeamupUserDetailsService detailsService;

    @Autowired
    public AuthController(JWTUtil jwtUtil, TeamupUserDetailsService detailsService, AuthenticationManager authenticationManager){
        this.jwtUtil = jwtUtil;
        this.detailsService = detailsService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> auth(@RequestBody AuthDTO authDTO) throws Exception {
        authenticate(authDTO.getUsername(), authDTO.getPassword());

        final UserDetails details = detailsService.loadUserByUsername(authDTO.getUsername());
        final String token = jwtUtil.create(details);

        return ResponseEntity.ok(new JWTDTO(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

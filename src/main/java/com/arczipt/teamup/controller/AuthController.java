package com.arczipt.teamup.controller;

import com.arczipt.teamup.dto.AuthDTO;
import com.arczipt.teamup.dto.JWTDTO;
import com.arczipt.teamup.dto.StatusDTO;
import com.arczipt.teamup.dto.UserRegisterDTO;
import com.arczipt.teamup.security.JWTUtil;
import com.arczipt.teamup.security.TeamupUserDetails;
import com.arczipt.teamup.service.TeamupUserDetailsService;
import com.arczipt.teamup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class AuthController {
    private JWTUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private TeamupUserDetailsService detailsService;
    private UserService userService;

    @Autowired
    public AuthController(JWTUtil jwtUtil, TeamupUserDetailsService detailsService, AuthenticationManager authenticationManager, UserService userService){
        this.jwtUtil = jwtUtil;
        this.detailsService = detailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    /**
     * Login. Authentication endpoint for existing users.
     *
     * @param authDTO - username and password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> auth(@RequestBody AuthDTO authDTO) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
        } catch (DisabledException e) {

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Bad credentials");
        }

        final TeamupUserDetails details = detailsService.loadUserByUsername(authDTO.getUsername());
        final String token = jwtUtil.create(details);

        return ResponseEntity.ok(new JWTDTO(details.getId(), token));
    }

    /**
     * Register new user.
     *
     * @param userRegisterDTO - new user data
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userRegisterDTO){
        return ResponseEntity.ok(new StatusDTO(userService.register(userRegisterDTO)));
    }
}

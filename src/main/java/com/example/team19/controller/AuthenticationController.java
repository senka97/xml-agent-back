package com.example.team19.controller;

import com.example.team19.dto.UserTokenState;
import com.example.team19.model.Agent;
import com.example.team19.security.TokenUtils;
import com.example.team19.security.auth.JwtAuthenticationRequest;
import com.example.team19.service.impl.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class AuthenticationController {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                       HttpServletResponse response) throws AuthenticationException, IOException {

        logger.info("Login");
        //System.out.println(passwordEncoder.encode("agent"));
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Agent agent = (Agent) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(agent.getEmail());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn)); //this dto object contains the token which is sent to the client
    }

   /* @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
        System.out.println("Radi autorizacija");
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        return new ResponseEntity(HttpStatus.OK);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }*/

}
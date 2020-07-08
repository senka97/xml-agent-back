package com.example.team19.service.impl;

import com.example.team19.model.Agent;
import com.example.team19.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private AgentRepository agentRepository;

   /* @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;*/

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Agent agent = agentRepository.findByEmail(email);
        if (agent == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        } else {
            return agent;
        }
    }

   /* // Function for a user's password change
    public void changePassword(String oldPassword, String newPassword) {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();

        if (authenticationManager != null) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, oldPassword));
        } else {
            return;
        }

        Agent agent = (Agent) loadUserByUsername(email);

        agent.setPassword(passwordEncoder.encode(newPassword));
        agentRepository.save(agent);

    }*/
}

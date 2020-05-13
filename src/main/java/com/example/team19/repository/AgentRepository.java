package com.example.team19.repository;

import com.example.team19.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent,Long> {

    Agent findByEmail(String email);
}

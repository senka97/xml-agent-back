package com.example.team19.service;

import com.example.team19.dto.MessageDTO;
import com.example.team19.dto.MessageRequestDTO;
import com.example.team19.dto.MessageResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MessageService {

    MessageDTO findMessagesForRequest(Long id);
    ResponseEntity<?> validateMessageRequest(MessageRequestDTO messageRequestDTO);
    MessageResponseDTO addMessage(MessageRequestDTO messageRequestDTO);
}

package com.example.team19.controller;

import com.example.team19.dto.MessageDTO;
import com.example.team19.dto.MessageRequestDTO;
import com.example.team19.dto.MessageResponseDTO;
import com.example.team19.model.Request;
import com.example.team19.service.impl.MessageServiceImpl;
import com.example.team19.service.impl.RequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/message")
@CrossOrigin
public class MessageController {


    @Autowired
    private MessageServiceImpl messageService;
    @Autowired
    private RequestServiceImpl requestService;

    @GetMapping(value = "/request/{id}", produces = "application/json")
    public ResponseEntity<?> getMessagesForRequest(@PathVariable("id") Long id){

        if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request id has to be positive long number.");
        }
        Request request = this.requestService.findById(id);
        if(request == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request with that id doesn't exist in the system.");
        }
        MessageDTO messageDTO = this.messageService.findMessagesForRequest(id);
        return new ResponseEntity(messageDTO, HttpStatus.OK);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addMessage(@RequestBody MessageRequestDTO messageRequestDTO){

        ResponseEntity<?> responseEntity = this.messageService.validateMessageRequest(messageRequestDTO);
        if(responseEntity != null){
            return responseEntity;
        }

        MessageResponseDTO mrs = this.messageService.addMessage(messageRequestDTO);
        if(mrs != null){
            return new ResponseEntity(mrs, HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on main app. Please contact technical support.");
        }

    }

}

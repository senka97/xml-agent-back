package com.example.team19.service.impl;

import com.example.team19.dto.MessageDTO;
import com.example.team19.dto.MessageRequestDTO;
import com.example.team19.dto.MessageResponseDTO;
import com.example.team19.model.Message;
import com.example.team19.model.Request;
import com.example.team19.repository.MessageRepository;
import com.example.team19.service.MessageService;
import com.example.team19.soap.RentClient;
import com.example.team19.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private RequestServiceImpl requestService;
    @Autowired
    private RentClient rentClient;


    @Override
    public ResponseEntity<?> validateMessageRequest(MessageRequestDTO messageRequestDTO) {
        boolean valid = true;
        String msg = "";
        if(messageRequestDTO.getRequestId() <= 0){
            msg += "Request id has to be positive long number.";
            valid = false;
        }
        if(messageRequestDTO.getContent() == null || messageRequestDTO.getContent().equals("")){
            msg += "Message content is mandatory.";
            valid = false;
        }
        if(!valid){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        Request request = this.requestService.findById(messageRequestDTO.getRequestId());
        if(request == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request with that id doesn't exist in the system.");
        }


        return null;
    }

    @Override
    public MessageDTO findMessagesForRequest(Long id) {

        Request r = this.requestService.findById(id);
        //pronadjem id poruka na glavnoj koje vec postoje za ovaj zahtev
        List<Long> existingMessages = this.messageRepository.findExistingMessageIds(id);

        GetMessagesResponse gmResponse = this.rentClient.getMessagesFromMainApp(r.getMainId(),existingMessages);

        //sacuvam nove poruke sa glavne u bazu agenta
        for(MessageResponseSOAP mrs: gmResponse.getMessageResponseSOAP()){
            Message m = new Message();
            m.setMainId(mrs.getMainId());
            m.setContent(mrs.getContent());
            m.setDateTime(LocalDateTime.parse(mrs.getDateTime()));
            m.setFromUserId(mrs.getFromUserId());
            m.setFromUserInfo(mrs.getFromUserInfo());
            m.setRequest(r);
            r.getMessages().add(m);
            this.messageRepository.save(m);
        }

        //sada izvucem iz baze osvezene poruke
        List<Message> messages = this.messageRepository.findMessagesForRequest(id);
        List<MessageResponseDTO> messageResponseDTO = new ArrayList<>();
        for(Message m: messages){
             messageResponseDTO.add(new MessageResponseDTO(m));
        }

        MessageDTO messageDTO = new MessageDTO(messageResponseDTO,gmResponse.getAgentId());

        return messageDTO;
    }

    @Override
    public MessageResponseDTO addMessage(MessageRequestDTO messageRequestDTO) {

        //prvo posaljem tu poruku na glavnu aplikaciju da se sacuva
        Request r = this.requestService.findById(messageRequestDTO.getRequestId());
        AddMessageResponse amResponse = this.rentClient.addMessage(r.getMainId(),messageRequestDTO.getContent());

        if(amResponse.isSuccess()){
            Message m = new Message();
            m.setMainId(amResponse.getMessageResponseSOAP().getMainId());
            m.setFromUserInfo(amResponse.getMessageResponseSOAP().getFromUserInfo());
            m.setFromUserId(amResponse.getMessageResponseSOAP().getFromUserId());
            m.setContent(amResponse.getMessageResponseSOAP().getContent());
            m.setDateTime(LocalDateTime.parse(amResponse.getMessageResponseSOAP().getDateTime()));
            m.setRequest(r);
            r.getMessages().add(m);
            m = this.messageRepository.save(m);
            return new MessageResponseDTO(m);
        }else{
            return null;
        }
    }
}

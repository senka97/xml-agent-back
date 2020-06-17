package com.example.team19.dto;

import java.util.List;

public class MessageDTO {

    private Long agentId; //ovo je id agenta na glavnoj app
    private List<MessageResponseDTO> messages;

    public MessageDTO(){

    }

    public MessageDTO(List<MessageResponseDTO> messages, Long agentId){
        this.messages = messages;
        this.agentId = agentId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public List<MessageResponseDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResponseDTO> messages) {
        this.messages = messages;
    }
}

package com.example.team19.dto;

public class NewReplyDTO {

    private String replyContent;

    public NewReplyDTO()
    {

    }

    public NewReplyDTO(String replyContent)
    {
        this.replyContent = replyContent;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}

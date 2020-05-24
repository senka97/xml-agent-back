package com.example.team19.dto;

import com.example.team19.model.Comment;

public class CommentDTO {

    String user;
    String text;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment) {
        user = comment.getFromComment();
        text = comment.getContent();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

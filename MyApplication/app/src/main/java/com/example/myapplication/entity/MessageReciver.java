package com.example.myapplication.entity;


import com.example.myapplication.DataBase.ConvertItem;

public class MessageReciver {
    private String chatID;
    private String created;
    private String content;
    private Sender sender;

    public MessageReciver(String chatID, String created, String content, Sender sender) {
        this.chatID = chatID;
        this.created = created;
        this.content = content;
        this.sender = sender;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
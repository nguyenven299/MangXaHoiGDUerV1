package com.example.Module;

public class Chats {
    private String sender;
    private String receiver;
    private String message;
    private String thoi_gian;

    public String getThoi_gian() {
        return thoi_gian;
    }

    public void setThoi_gian(String thoi_gian) {
        this.thoi_gian = thoi_gian;
    }

    public Chats(String sender, String receiver, String message, String thoi_gian) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.thoi_gian = thoi_gian;
    }

    public Chats() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }





}

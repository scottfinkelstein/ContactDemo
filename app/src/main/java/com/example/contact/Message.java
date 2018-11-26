package com.example.contact;

public class Message {
    public String to;
    public String subject;
    public String message;

    public Message() {

    }

    public Message(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
}

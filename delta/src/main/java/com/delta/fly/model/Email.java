package com.delta.fly.model;

public class Email {

    @javax.validation.constraints.Email
    private String to;
    private String subject;
    private String message;
    private String link;

    public String getFrom() {
        @javax.validation.constraints.Email String from = "www.delta.fly@gmail.com";
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

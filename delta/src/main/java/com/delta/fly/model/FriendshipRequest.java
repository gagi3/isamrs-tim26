package com.delta.fly.model;

import java.io.Serializable;

public class FriendshipRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Passenger sentFrom;
    private Passenger sentTo;
    private Boolean accepted;

    public FriendshipRequest(Passenger sentFrom, Passenger sentTo) {
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
        this.accepted = false;
    }

    public Long getId() {
        return id;
    }

    public Passenger getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(Passenger sentFrom) {
        this.sentFrom = sentFrom;
    }

    public Passenger getSentTo() {
        return sentTo;
    }

    public void setSentTo(Passenger sentTo) {
        this.sentTo = sentTo;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}

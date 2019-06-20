package com.delta.fly.dto;

import com.delta.fly.model.Passenger;

public class FriendshipDTO {

    private Passenger from;
    private Passenger to;

    public Passenger getFrom() {
        return from;
    }

    public void setFrom(Passenger from) {
        this.from = from;
    }

    public Passenger getTo() {
        return to;
    }

    public void setTo(Passenger to) {
        this.to = to;
    }
}

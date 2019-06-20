package com.delta.fly.dto;

import com.delta.fly.model.Passenger;
import com.delta.fly.model.Ticket;

public class FriendReservationDTO {

    private Ticket ticket;
    private Passenger passenger;
    private Integer luggage;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Integer getLuggage() {
        return luggage;
    }

    public void setLuggage(Integer luggage) {
        this.luggage = luggage;
    }
}

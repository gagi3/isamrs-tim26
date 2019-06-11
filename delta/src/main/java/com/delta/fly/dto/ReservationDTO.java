package com.delta.fly.dto;

import com.delta.fly.model.Ticket;

public class ReservationDTO {

    private Ticket ticket;
    private Integer luggage;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Integer getLuggage() {
        return luggage;
    }

    public void setLuggage(Integer luggage) {
        this.luggage = luggage;
    }
}

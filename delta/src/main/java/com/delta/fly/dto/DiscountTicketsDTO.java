package com.delta.fly.dto;

import com.delta.fly.model.Ticket;

import java.util.List;

public class DiscountTicketsDTO {
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}

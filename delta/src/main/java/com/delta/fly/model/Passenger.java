package com.delta.fly.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Passenger extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Boolean deleted;
    private List<Ticket> tickets = new ArrayList<>();
    private List<Passenger> friends = new ArrayList<>();

    public Passenger(String email, String password, String firstName, String lastName, String city, String phoneNumber, Boolean deleted, List<Ticket> tickets, List<Passenger> friends) {
        super(email, password, firstName, lastName, city, phoneNumber);
        this.deleted = deleted;
        this.tickets = tickets;
        this.friends = friends;
    }

    public Passenger(String email, String password, String firstName, String lastName, String city, String phoneNumber, Boolean deleted, List<Ticket> tickets) {
        super(email, password, firstName, lastName, city, phoneNumber);
        this.deleted = deleted;
        this.tickets = tickets;
    }

    public Passenger(String email, String password, String firstName, String lastName, String city, String phoneNumber, Boolean deleted) {
        super(email, password, firstName, lastName, city, phoneNumber);
        this.deleted = deleted;
    }

    public Passenger(String email, String password, String firstName, String lastName, String city, String phoneNumber) {
        super(email, password, firstName, lastName, city, phoneNumber);
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Passenger> getFriends() {
        return friends;
    }

    public void setFriends(List<Passenger> friends) {
        this.friends = friends;
    }
}

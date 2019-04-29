package com.delta.fly.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "passenger")
public class Passenger extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "passenger", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Passenger.class, cascade = CascadeType.ALL)
    private List<Passenger> friends = new ArrayList<>();

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    public Passenger() {
    }

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

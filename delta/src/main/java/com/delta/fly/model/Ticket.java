package com.delta.fly.model;

import java.io.Serializable;

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Flight flight;
    private Seat seat;
    private Double price;
    private Passenger passenger;

    public Ticket(Flight flight, Seat seat, Double price, Passenger passenger) {
        this.flight = flight;
        this.seat = seat;
        this.price = price;
        this.passenger = passenger;
    }

    public Long getId() {
        return id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}

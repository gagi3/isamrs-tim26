package com.delta.fly.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Airplane implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private AirlineCompany airlineCompany;
    private String name;
    private List<Seat> seats = new ArrayList<>();

    public Airplane(AirlineCompany airlineCompany, String name, List<Seat> seats) {
        this.airlineCompany = airlineCompany;
        this.name = name;
        this.seats = seats;
    }

    public Airplane(AirlineCompany airlineCompany, String name) {
        this.airlineCompany = airlineCompany;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public AirlineCompany getAirlineCompany() {
        return airlineCompany;
    }

    public void setAirlineCompany(AirlineCompany airlineCompany) {
        this.airlineCompany = airlineCompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}

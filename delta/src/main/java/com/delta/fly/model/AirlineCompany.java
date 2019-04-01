package com.delta.fly.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AirlineCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String address;
    private String description;
    private List<String> destinations = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();
    private List<Ticket> discountedTickets = new ArrayList<>();
    private List<Airplane> airplanes = new ArrayList<>();
    private Double priceByKm;
    private Double luggagePriceByItem;

    public AirlineCompany(String name, String address, String description, List<String> destinations, List<Flight> flights, List<Ticket> discountedTickets, List<Airplane> airplanes, Double priceByKm, Double luggagePriceByItem) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.destinations = destinations;
        this.flights = flights;
        this.discountedTickets = discountedTickets;
        this.airplanes = airplanes;
        this.priceByKm = priceByKm;
        this.luggagePriceByItem = luggagePriceByItem;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Ticket> getDiscountedTickets() {
        return discountedTickets;
    }

    public void setDiscountedTickets(List<Ticket> discountedTickets) {
        this.discountedTickets = discountedTickets;
    }

    public List<Airplane> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(List<Airplane> airplanes) {
        this.airplanes = airplanes;
    }

    public Double getPriceByKm() {
        return priceByKm;
    }

    public void setPriceByKm(Double priceByKm) {
        this.priceByKm = priceByKm;
    }

    public Double getLuggagePriceByItem() {
        return luggagePriceByItem;
    }

    public void setLuggagePriceByItem(Double luggagePriceByItem) {
        this.luggagePriceByItem = luggagePriceByItem;
    }
}

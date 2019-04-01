package com.delta.fly.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private AirlineCompany airlineCompany;
    private Airplane airplane;

    private String pointOfDeparture;
    private Date departureTime;
    private List<String> transfers = new ArrayList<>();
    private String destination;
    private Date arrivalTime;

    private Integer distance;
    private Long travelTime;
    private Double ticketPrice;

    private List<Ticket> tickets = new ArrayList<>();

    public Flight(AirlineCompany airlineCompany, Airplane airplane, String pointOfDeparture, Date departureTime, List<String> transfers, String destination, Date arrivalTime, Integer distance, Long travelTime, Double ticketPrice, List<Ticket> tickets) {
        this.airlineCompany = airlineCompany;
        this.airplane = airplane;
        this.pointOfDeparture = pointOfDeparture;
        this.departureTime = departureTime;
        this.transfers = transfers;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.distance = distance;
        this.travelTime = travelTime;
        this.ticketPrice = ticketPrice;
        this.tickets = tickets;
    }

    public Flight(AirlineCompany airlineCompany, Airplane airplane, String pointOfDeparture, Date departureTime, List<String> transfers, String destination, Date arrivalTime, Integer distance, Long travelTime, Double ticketPrice) {
        this.airlineCompany = airlineCompany;
        this.airplane = airplane;
        this.pointOfDeparture = pointOfDeparture;
        this.departureTime = departureTime;
        this.transfers = transfers;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.distance = distance;
        this.travelTime = travelTime;
        this.ticketPrice = ticketPrice;
    }

    public Flight(AirlineCompany airlineCompany, Airplane airplane, String pointOfDeparture, Date departureTime, List<String> transfers, String destination, Date arrivalTime, Integer distance, Double ticketPrice) {
        this.airlineCompany = airlineCompany;
        this.airplane = airplane;
        this.pointOfDeparture = pointOfDeparture;
        this.departureTime = departureTime;
        this.transfers = transfers;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.distance = distance;
        this.travelTime = arrivalTime.getTime() - departureTime.getTime();
        this.ticketPrice = ticketPrice;
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

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public String getPointOfDeparture() {
        return pointOfDeparture;
    }

    public void setPointOfDeparture(String pointOfDeparture) {
        this.pointOfDeparture = pointOfDeparture;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public List<String> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<String> transfers) {
        this.transfers = transfers;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Long travelTime) {
        this.travelTime = travelTime;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}

package com.delta.fly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flight")
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airline_company", referencedColumnName = "id")
    @JsonIgnore
    private AirlineCompany airlineCompany;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airplane", referencedColumnName = "id")
    private Airplane airplane;

    @OneToOne(optional = false)
    @JoinColumn(name = "departure", referencedColumnName = "id")
    private PlaceAndTime departure;

    @OneToMany
    @JoinTable(name = "transfers", joinColumns = @JoinColumn(name = "flight_id"), inverseJoinColumns = @JoinColumn(name = "place_and_time_id"))
    private List<PlaceAndTime> transfers;

    @OneToOne(optional = false)
    @JoinColumn(name = "arrival", referencedColumnName = "id")
    private PlaceAndTime arrival;


    @Column(name = "distance", unique = false, nullable = false)
    private Integer distance;

    @Column(name = "travel_time", unique = false, nullable = false)
    private Long travelTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "flight", cascade = CascadeType.ALL)
//    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    public Flight() {
    }

    public Flight(AirlineCompany airlineCompany, Airplane airplane, PlaceAndTime departure, List<PlaceAndTime> transfers, PlaceAndTime arrival, Integer distance, Long travelTime) {
        this.airlineCompany = airlineCompany;
        this.airplane = airplane;
        this.departure = departure;
        this.transfers = transfers;
        this.arrival = arrival;
        this.distance = distance;
        this.travelTime = travelTime;
        this.deleted = false;
    }

    public Flight(Airplane airplane, PlaceAndTime departure, List<PlaceAndTime> transfers, PlaceAndTime arrival, Integer distance, Long travelTime) {
        this.airplane = airplane;
        this.departure = departure;
        this.transfers = transfers;
        this.arrival = arrival;
        this.distance = distance;
        this.travelTime = travelTime;
        this.deleted = false;
    }

    public Flight(AirlineCompany airlineCompany, Airplane airplane, PlaceAndTime departure, PlaceAndTime arrival, Integer distance, Long travelTime) {
        this.airlineCompany = airlineCompany;
        this.airplane = airplane;
        this.departure = departure;
        this.arrival = arrival;
        this.distance = distance;
        this.travelTime = travelTime;
        this.deleted = false;
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

    public PlaceAndTime getDeparture() {
        return departure;
    }

    public void setDeparture(PlaceAndTime departure) {
        this.departure = departure;
    }

    public List<PlaceAndTime> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<PlaceAndTime> transfers) {
        this.transfers = transfers;
    }

    public PlaceAndTime getArrival() {
        return arrival;
    }

    public void setArrival(PlaceAndTime arrival) {
        this.arrival = arrival;
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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}

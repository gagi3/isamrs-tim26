package com.delta.fly.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airline_company")
public class AirlineCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "company_name", unique = true, nullable = false)
    private String name;

    @Column(name = "address", unique = true, nullable = false)
    private String address;

    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @Column(name = "destinations", unique = false, nullable = false)
    @ElementCollection(targetClass = String.class)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<String> destinations = new ArrayList<>();

    // Flight - owner
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airlineCompany", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Flight> flights = new ArrayList<>();

    // New table
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "discounted_tickets", joinColumns = @JoinColumn(name = "airline_company_id"), inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    private List<Ticket> discountedTickets = new ArrayList<>();

    // Airplane - owner
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airlineCompany", cascade = CascadeType.ALL)
//    @JsonIgnore
    private List<Airplane> airplanes = new ArrayList<>();

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    public AirlineCompany() {
    }

    public AirlineCompany(String name, String address, String description, List<String> destinations, List<Flight> flights, List<Ticket> discountedTickets, List<Airplane> airplanes) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.destinations = destinations;
        this.flights = flights;
        this.discountedTickets = discountedTickets;
        this.airplanes = airplanes;
        this.deleted = false;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}

package com.delta.fly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "flight", referencedColumnName = "id")
    @JsonIgnore
    private Flight flight;

    @OneToOne(targetEntity = Seat.class)
    @JoinColumn(name = "seat", referencedColumnName = "id")
    private Seat seat;

    @Column(name = "price", unique = false, nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "passenger", referencedColumnName = "id", nullable = true, columnDefinition = "BIGINT(20) NULL")
    @ColumnDefault("BIGINT(20) NULL")
    private Passenger passenger;

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    public Ticket() {
    }

    public Ticket(Flight flight, Seat seat, Double price, Passenger passenger) {
        this.flight = flight;
        this.seat = seat;
        this.price = price;
        this.passenger = passenger;
        this.deleted = false;
    }

    public Ticket(Flight flight, Seat seat, Passenger passenger) {
        this.flight = flight;
        this.seat = seat;
        this.passenger = passenger;
        this.deleted = false;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}

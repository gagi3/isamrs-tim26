package com.delta.fly.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ticket", referencedColumnName = "id")
    private Ticket ticket;

    @OneToOne
    @JoinColumn(name = "passenger", referencedColumnName = "id")
    @JsonIgnore
    private Passenger passenger;

    @Column(name = "reservation_date", unique = false, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy. HH:mm")
    private Date reservationDate;

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}

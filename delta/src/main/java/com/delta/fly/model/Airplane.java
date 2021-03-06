package com.delta.fly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airplane")
public class Airplane implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airline_company", referencedColumnName = "id")
    @JsonIgnore
    private AirlineCompany airlineCompany;

    @Column(name = "airplane_name", unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airplane", cascade = CascadeType.ALL)
//    @JsonIgnore
    private List<Seat> seats = new ArrayList<>();

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    public Airplane() {
    }

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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}

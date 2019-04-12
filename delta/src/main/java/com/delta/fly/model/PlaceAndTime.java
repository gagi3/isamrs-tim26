package com.delta.fly.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "place_and_time")
public class PlaceAndTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "the_place", unique = false, nullable = false)
    private String thePlace;

    @Column(name = "the_time", unique = false, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy. HH:mm")
    private Date theTime;

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    public PlaceAndTime(String thePlace, Date theTime) {
        this.thePlace = thePlace;
        this.theTime = theTime;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public String getThePlace() {
        return thePlace;
    }

    public void setThePlace(String thePlace) {
        this.thePlace = thePlace;
    }

    public Date getTheTime() {
        return theTime;
    }

    public void setTheTime(Date theTime) {
        this.theTime = theTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}

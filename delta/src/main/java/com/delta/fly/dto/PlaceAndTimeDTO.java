package com.delta.fly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PlaceAndTimeDTO {

    private String thePlace;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy. HH:mm")
    private Date theTime;

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
}

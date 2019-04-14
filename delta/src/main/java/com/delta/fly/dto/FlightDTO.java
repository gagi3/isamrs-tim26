package com.delta.fly.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class FlightDTO {

    private Long airplaneID;
    private Long airlineCompanyID;
    private PlaceAndTimeDTO departure;
    private PlaceAndTimeDTO arrival;
    private List<PlaceAndTimeDTO> transfers;
    private Integer distance;
    private Long travelTime;
    private Boolean generateTickets;

    public FlightDTO() {
    }

    public Long getAirplaneID() {
        return airplaneID;
    }

    public void setAirplaneID(Long airplaneID) {
        this.airplaneID = airplaneID;
    }

    public Long getAirlineCompanyID() {
        return airlineCompanyID;
    }

    public void setAirlineCompanyID(Long airlineCompanyID) {
        this.airlineCompanyID = airlineCompanyID;
    }

    public PlaceAndTimeDTO getDeparture() {
        return departure;
    }

    public void setDeparture(PlaceAndTimeDTO departure) {
        this.departure = departure;
    }

    public PlaceAndTimeDTO getArrival() {
        return arrival;
    }

    public void setArrival(PlaceAndTimeDTO arrival) {
        this.arrival = arrival;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public List<PlaceAndTimeDTO> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<PlaceAndTimeDTO> transfers) {
        this.transfers = transfers;
    }

    public Long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Long travelTime) {
        this.travelTime = travelTime;
    }

    public Boolean getGenerateTickets() {
        return generateTickets;
    }

    public void setGenerateTickets(Boolean generateTickets) {
        this.generateTickets = generateTickets;
    }
}

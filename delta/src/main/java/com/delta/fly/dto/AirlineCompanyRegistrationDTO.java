package com.delta.fly.dto;

import java.util.List;

public class AirlineCompanyRegistrationDTO {

    private String name;
    private String address;
    private String description;
    private List<String> destinations;
    private Double priceByKm;
    private Double luggagePriceByItem;

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

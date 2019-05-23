package com.delta.fly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

public class AirlineCompanyRegistrationDTO {

    private String name;
    private String address;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<String> destinations;
    private RegisterDTO admin;

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

    public RegisterDTO getAdmin() {
        return admin;
    }

    public void setAdmin(RegisterDTO admin) {
        this.admin = admin;
    }

}

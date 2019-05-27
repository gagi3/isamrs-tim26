package com.delta.fly.dto;

public class PriceListDTO {

    private Double priceByKm;
    private Double priceByLuggageItem;
    private Double businessClassPriceCoefficient;
    private Double economyClassPriceCoefficient;
    private Double firstClassPriceCoefficient;
    private Double discountPercentage;

    public PriceListDTO() {
    }

    public Double getPriceByKm() {
        return priceByKm;
    }

    public void setPriceByKm(Double priceByKm) {
        this.priceByKm = priceByKm;
    }

    public Double getPriceByLuggageItem() {
        return priceByLuggageItem;
    }

    public void setPriceByLuggageItem(Double priceByLuggageItem) {
        this.priceByLuggageItem = priceByLuggageItem;
    }

    public Double getBusinessClassPriceCoefficient() {
        return businessClassPriceCoefficient;
    }

    public void setBusinessClassPriceCoefficient(Double businessClassPriceCoefficient) {
        this.businessClassPriceCoefficient = businessClassPriceCoefficient;
    }

    public Double getEconomyClassPriceCoefficient() {
        return economyClassPriceCoefficient;
    }

    public void setEconomyClassPriceCoefficient(Double economyClassPriceCoefficient) {
        this.economyClassPriceCoefficient = economyClassPriceCoefficient;
    }

    public Double getFirstClassPriceCoefficient() {
        return firstClassPriceCoefficient;
    }

    public void setFirstClassPriceCoefficient(Double firstClassPriceCoefficient) {
        this.firstClassPriceCoefficient = firstClassPriceCoefficient;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}

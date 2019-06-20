package com.delta.fly.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Table(name = "price_list")
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "price_by_km", unique = false, nullable = false)
    @Range(min = 0)
    private Double priceByKm;

    @Column(name = "price_by_luggage_item", unique = false, nullable = false)
    @Range(min = 0)
    private Double priceByLuggageItem;

    @Column(name = "business_class_price_coefficient", unique = false, nullable = false)
    @Range(min = 1)
    private Double businessClassPriceCoefficient;

    @Column(name = "economy_class_price_coefficient", unique = false, nullable = false)
    @Range(min = 1)
    private Double economyClassPriceCoefficient;

    @Column(name = "first_class_price_coefficient", unique = false, nullable = false)
    @Range(min = 1)
    private Double firstClassPriceCoefficient;

    @Column(name = "discount_percentage", unique = false, nullable = false)
    @Range(min = 0, max = 100)
    private Double discountPercentage;

    @OneToOne
    @JoinColumn(name = "company", referencedColumnName = "id", unique = true)
    private AirlineCompany airlineCompany;

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    public Long getId() {
        return id;
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

    public AirlineCompany getAirlineCompany() {
        return airlineCompany;
    }

    public void setAirlineCompany(AirlineCompany airlineCompany) {
        this.airlineCompany = airlineCompany;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}

package com.delta.fly.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "airline_company_admin")
public class AirlineCompanyAdmin extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    @JoinColumn(name = "airline_company", referencedColumnName = "id")
    private AirlineCompany airlineCompany;

    public AirlineCompanyAdmin() {
    }

    public AirlineCompanyAdmin(String email, String password, String firstName, String lastName, String city, String phoneNumber, Boolean deleted, AirlineCompany airlineCompany) {
        super(email, password, firstName, lastName, city, phoneNumber);
        this.deleted = deleted;
        this.airlineCompany = airlineCompany;
    }

    public AirlineCompanyAdmin(String email, String password, String firstName, String lastName, String city, String phoneNumber, Boolean deleted) {
        super(email, password, firstName, lastName, city, phoneNumber);
        this.deleted = deleted;
    }

    public AirlineCompanyAdmin(String email, String password, String firstName, String lastName, String city, String phoneNumber, AirlineCompany airlineCompany) {
        super(email, password, firstName, lastName, city, phoneNumber);
        this.airlineCompany = airlineCompany;
    }

    public AirlineCompanyAdmin(String email, String password, String firstName, String lastName, String city, String phoneNumber) {
        super(email, password, firstName, lastName, city, phoneNumber);
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public AirlineCompany getAirlineCompany() {
        return airlineCompany;
    }

    public void setAirlineCompany(AirlineCompany airlineCompany) {
        this.airlineCompany = airlineCompany;
    }
}

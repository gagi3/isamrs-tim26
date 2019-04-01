package com.delta.fly.model;

import java.io.Serializable;

public class SystemAdmin extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Boolean deleted;

    public SystemAdmin(String email, String password, String firstName, String lastName, String city, String phoneNumber, Boolean deleted) {
        super(email, password, firstName, lastName, city, phoneNumber);
        this.deleted = deleted;
    }

    public SystemAdmin(String email, String password, String firstName, String lastName, String city, String phoneNumber) {
        super(email, password, firstName, lastName, city, phoneNumber);
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}

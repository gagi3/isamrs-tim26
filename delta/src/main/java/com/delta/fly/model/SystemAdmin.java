package com.delta.fly.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "system_admin")
public class SystemAdmin extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    public SystemAdmin() {
    }

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

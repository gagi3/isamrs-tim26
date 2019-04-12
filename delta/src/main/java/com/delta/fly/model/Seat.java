package com.delta.fly.model;

import com.delta.fly.enumeration.Class;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "seat")
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "seat_row", unique = false, nullable = false)
    private Integer row;

    @Column(name = "seat_column", unique = false, nullable = false)
    private Integer column;

    @Column(name = "seat_class", unique = false, nullable = false)
    private Class seatClass;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airplane", referencedColumnName = "id")
    private Airplane airplane;

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    public Seat(Integer row, Integer column, Class seatClass) {
        this.row = row;
        this.column = column;
        this.seatClass = seatClass;
        this.deleted = false;
    }

    public Seat(Integer row, Integer column) {
        this.row = row;
        this.column = column;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Class getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(Class seatClass) {
        this.seatClass = seatClass;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}

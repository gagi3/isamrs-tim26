package com.delta.fly.model;

import java.io.Serializable;

public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer row;
    private Integer column;

    public Seat(Integer row, Integer column) {
        this.row = row;
        this.column = column;
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
}

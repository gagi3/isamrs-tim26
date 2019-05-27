package com.delta.fly.dto;

import com.delta.fly.enumeration.Class;

public class SeatDTO {

    private Integer rowNo;
    private Integer colNo;
    private Class seatClass;

    public Integer getRowNo() {
        return rowNo;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public Integer getColNo() {
        return colNo;
    }

    public void setColNo(Integer colNo) {
        this.colNo = colNo;
    }

    public Class getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(Class seatClass) {
        this.seatClass = seatClass;
    }
}

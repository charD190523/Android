package com.example.cinemaapp.model;

import java.io.Serializable;
import java.util.List;

public class Seat implements Serializable {
    private Integer id;

    private String seatName;

    private String type;

    private Float price;

    public Seat(Integer id, String seatName, String type, Float price) {
        this.id = id;
        this.seatName = seatName;
        this.type = type;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}

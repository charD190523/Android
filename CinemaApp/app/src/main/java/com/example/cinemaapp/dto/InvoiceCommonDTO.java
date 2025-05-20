package com.example.cinemaapp.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class InvoiceCommonDTO implements Serializable {

    private Integer id;
    private String movieName;
    private String showDate;
    private String startTime;
    private Float totalPrice;

    public InvoiceCommonDTO(Integer id, String movieName, String showDate, String startTime, Float totalPrice) {
        this.id = id;
        this.movieName = movieName;
        this.showDate = showDate;
        this.startTime = startTime;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}

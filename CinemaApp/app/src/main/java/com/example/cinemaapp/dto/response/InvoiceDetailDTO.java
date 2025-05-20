package com.example.cinemaapp.dto.response;

import com.example.cinemaapp.model.Seat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class InvoiceDetailDTO implements Serializable {
    private String movieName;
    private String showDate;
    private String startTime;
    private List<Seat> seats;
    private Float totalPrice;
    private Double ticketPrice;
    private Float foodPrice;

    public InvoiceDetailDTO(String movieName, String showDate, String startTime, List<Seat> seats, Float totalPrice, Double ticketPrice, Float foodPrice) {
        this.movieName = movieName;
        this.showDate = showDate;
        this.startTime = startTime;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.ticketPrice = ticketPrice;
        this.foodPrice = foodPrice;
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

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Float getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Float foodPrice) {
        this.foodPrice = foodPrice;
    }
}
